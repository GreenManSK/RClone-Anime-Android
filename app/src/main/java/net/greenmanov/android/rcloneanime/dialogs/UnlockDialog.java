package net.greenmanov.android.rcloneanime.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import net.greenmanov.android.rcloneanime.R;

public class UnlockDialog extends DialogFragment {

    private TextInputEditText inputEditText;
    private Spinner durationSpinner;
    private int[] valuesArray;

    private OnSubmitListener listener;

    public UnlockDialog(OnSubmitListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.unlock);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_unlock, null);
        builder.setView(view);

        builder.setPositiveButton(R.string.ok, (dialog, id) -> {
            int duration = valuesArray[durationSpinner.getSelectedItemPosition()] * 1000;
            listener.OnSubimt(inputEditText.getText().toString(), duration);
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {

        });

        inputEditText = view.findViewById(R.id.password);
        durationSpinner = view.findViewById(R.id.duration_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.durations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adapter);

        valuesArray = getResources().getIntArray(R.array.durations_values_seconds);

        return builder.create();
    }

    @FunctionalInterface
    public interface OnSubmitListener {
        void OnSubimt(String password, int durationInMs);
    }
}
