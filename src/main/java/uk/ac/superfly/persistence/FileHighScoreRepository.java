package uk.ac.superfly.persistence;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public final class FileHighScoreRepository implements HighScoreRepository {
    private final Path scoreFile;

    public FileHighScoreRepository(Path scoreFile) {
        this.scoreFile = Objects.requireNonNull(scoreFile, "scoreFile")
                .toAbsolutePath()
                .normalize();
    }

    @Override
    public int load() {
        if (Files.notExists(scoreFile)) {
            return 0;
        }

        final String storedValue;
        try {
            storedValue = Files.readString(scoreFile, StandardCharsets.UTF_8).trim();
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not read high score from " + scoreFile, exception);
        }

        final int score;
        try {
            score = Integer.parseInt(storedValue);
        } catch (NumberFormatException exception) {
            throw corruptScore(exception);
        }

        if (score < 0) {
            throw corruptScore(null);
        }
        return score;
    }

    @Override
    public void save(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("High score cannot be negative");
        }

        Path temporaryFile = null;
        try {
            Path parent = scoreFile.getParent();
            Files.createDirectories(parent);
            temporaryFile = Files.createTempFile(parent, "high-score-", ".tmp");
            Files.writeString(temporaryFile, Integer.toString(score), StandardCharsets.UTF_8);
            replaceScoreFile(temporaryFile);
        } catch (IOException exception) {
            cleanUpAfterFailedSave(temporaryFile, exception);
            throw new UncheckedIOException("Could not save high score to " + scoreFile, exception);
        }
    }

    private void replaceScoreFile(Path temporaryFile) throws IOException {
        try {
            Files.move(
                    temporaryFile,
                    scoreFile,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (AtomicMoveNotSupportedException exception) {
            Files.move(temporaryFile, scoreFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void cleanUpAfterFailedSave(Path temporaryFile, IOException originalException) {
        if (temporaryFile == null) {
            return;
        }
        try {
            Files.deleteIfExists(temporaryFile);
        } catch (IOException cleanupException) {
            originalException.addSuppressed(cleanupException);
        }
    }

    private IllegalStateException corruptScore(NumberFormatException cause) {
        return new IllegalStateException(
                "High-score file contains an invalid non-negative integer: " + scoreFile,
                cause
        );
    }
}
