package uk.ac.superfly.persistence;

public interface HighScoreRepository {
    int load();

    void save(int score);
}
