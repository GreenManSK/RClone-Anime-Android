package net.greenmanov.android.rcloneanime.service;

import net.greenmanov.android.rcloneanime.data.Anime;
import net.greenmanov.android.rcloneanime.data.AnimeEntity;
import net.greenmanov.android.rcloneanime.data.AnimeFileEntity;
import net.greenmanov.android.rcloneanime.data.Drive;
import net.greenmanov.android.rcloneanime.persistance.AnimeDao;
import net.greenmanov.android.rcloneanime.persistance.AnimeFileDao;
import net.greenmanov.android.rcloneanime.persistance.DriveDao;
import net.greenmanov.android.rcloneanime.rclone.RCloneException;
import net.greenmanov.android.rcloneanime.rclone.RCloneRunner;
import net.greenmanov.android.rcloneanime.rclone.RcloneFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RefreshService {

    private RCloneRunner rclone;
    private DriveDao driveDao;
    private AnimeDao animeDao;
    private AnimeFileDao animeFileDao;

    public RefreshService(RCloneRunner rclone, DriveDao driveDao, AnimeDao animeDao, AnimeFileDao animeFileDao) {
        this.driveDao = driveDao;
        this.animeDao = animeDao;
        this.animeFileDao = animeFileDao;
    }

    public void Refresh(Drive drive) throws RCloneException {
        List<RcloneFile> files = rclone.ls(drive.getName(), drive.getPath());
        Map<String, Set<String>> animeMap = createAnimeMap(files);

        Map<String, AnimeEntity> entityMap = new HashMap<>();
        for (AnimeEntity anime : drive.getAnimeList()) {
            if (!animeMap.containsKey(anime.getName())) {
                animeDao.delete(anime);
            } else {
                entityMap.put(anime.getName(), anime);
                anime.setWatched(drive.isWatched());
                animeDao.update(anime);
            }
        }

        for (String animeName : animeMap.keySet()) {
            Set<String> animeFiles = animeMap.get(animeName);
            if (entityMap.containsKey(animeName)) {
                updateAnime(entityMap.get(animeName), animeFiles);
            } else {
                addAnime(drive, animeName, animeFiles);
            }

        }
    }

    private void addAnime(Drive drive, String animeName, Set<String> animeFiles) {
        AnimeEntity entity = new AnimeEntity();
        entity.setName(animeName);
        entity.setWatched(drive.isWatched());
        long animeId = animeDao.insert(entity);
        for (String file : animeFiles) {
            AnimeFileEntity fileEntity = new AnimeFileEntity();
            fileEntity.setName(file);
            fileEntity.setAnimeId((int) animeId);
            animeFileDao.insert(fileEntity);
        }
    }

    private void updateAnime(AnimeEntity animeEntity, Set<String> animeFiles) {
        List<AnimeFileEntity> dbFiles = animeFileDao.listSync(animeEntity.getId());
        for (AnimeFileEntity file : dbFiles) {
            if (!animeFiles.contains(file.getName())) {
                animeFileDao.delete(file);
            } else {
                animeFiles.remove(file.getName());
            }
        }

        for (String fileName : animeFiles) {
            AnimeFileEntity entity = new AnimeFileEntity();
            entity.setName(fileName);;
            entity.setAnimeId(animeEntity.getId());
            animeFileDao.insert(entity);
        }
    }

    private Map<String, Set<String>> createAnimeMap(List<RcloneFile> files) {
        Map<String, Set<String>> result = new HashMap<>();

        files.stream().filter(RcloneFile::isDir).forEach(f -> result.put(f.getName(), new HashSet<>()));
        files.stream().filter(f -> !f.isDir()).forEach(f -> {
            Path path = Paths.get(f.getPath());
            String animeName = getRootDir(path);
            String fileName = path.getFileName().toString();
            if (result.containsKey(animeName)) {
                result.get(animeName).add(fileName);
            }
        });

        return result;
    }

    private String getRootDir(Path path) {
        while (path.getParent() != null) {
            path = path.getParent();
        }
        return path.getFileName().toString();
    }
}
