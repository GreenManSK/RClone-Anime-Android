package net.greenmanov.android.rcloneanime.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.data.DriveEntity;
import net.greenmanov.android.rcloneanime.persistance.AppDatabase;

public class DriveDialog extends DialogFragment {

    private Context context;
    private AppDatabase database;
    private DriveEntity entity;

    private OnSubmitListener onSubmitListener;

    private TextInputEditText driveNameInput;
    private TextInputEditText drivePathInput;
    private SwitchMaterial watchedSwitch;

    private AlertDialog dialog;

    public DriveDialog(Context context) {
        this.context = context;
        database = AppDatabase.getInstance(context);
    }

    public DriveDialog(Context context, DriveEntity entity) {
        this(context);
        this.entity = entity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(entity == null ? R.string.add : R.string.edit);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_drive, null);
        builder.setView(view);

        builder.setPositiveButton(R.string.save, (dialog, id) -> {
            saveEntity();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {

        });

        driveNameInput = view.findViewById(R.id.driveName);
        drivePathInput = view.findViewById(R.id.drivePath);
        watchedSwitch = view.findViewById(R.id.watched);

        TextWatcher textWatcher =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(isValid());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        driveNameInput.addTextChangedListener(textWatcher);
        drivePathInput.addTextChangedListener(textWatcher);

        dialog = builder.create();
        return dialog;
    }

    private boolean isValid() {
        if (driveNameInput.getText().toString().isEmpty()) {
            return false;
        }
        if (drivePathInput.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (entity != null) {
            driveNameInput.setText(entity.getName());
            drivePathInput.setText(entity.getPath());
            watchedSwitch.setChecked(entity.isWatched());
        }

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(isValid());
    }

    @SuppressLint("StaticFieldLeak")
    private void saveEntity() {
        boolean insert = false;
        if (entity == null) {
            entity = new DriveEntity();
            insert = true;
        }
        entity.setName(driveNameInput.getText().toString());
        entity.setPath(drivePathInput.getText().toString());
        entity.setWatched(watchedSwitch.isChecked());

        boolean finalInsert = insert;
        new AsyncTask<DriveEntity, Void, DriveEntity>() {
            @Override
            protected DriveEntity doInBackground(DriveEntity... driveEntities) {
                if (finalInsert) {
                    entity.setId((int) database.driveDao().insert(entity));
                } else {
                    database.driveDao().update(entity);
                }
                return entity;
            }

            @Override
            protected void onPostExecute(DriveEntity driveEntity) {
                if (onSubmitListener != null) {
                    onSubmitListener.onSubmit();
                }
            }
        }.execute(entity);
    }

    public OnSubmitListener getOnSubmitListener() {
        return onSubmitListener;
    }

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    @FunctionalInterface
    public interface OnSubmitListener {
        void onSubmit();
    }
}
