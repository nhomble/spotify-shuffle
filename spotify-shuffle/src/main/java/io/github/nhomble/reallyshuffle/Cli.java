package io.github.nhomble.reallyshuffle;

import org.apache.hc.core5.http.ParseException;
import picocli.CommandLine;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Playlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cli implements Runnable {
    @CommandLine.Option(names = "-p", required = true)
    private String playlistId;

    @CommandLine.Option(names = "-t", required = true)
    private String refreshToken;

    @CommandLine.Option(names = "-s", required = true)
    private String clientSecret;

    @CommandLine.Option(names = "-v")
    private boolean verbose = false;

    private void debug(String message) {
        if (verbose) {
            System.out.printf("[DEBUG] %s%n", message);
        }
    }

    @Override
    public void run() {
        try {
            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setClientId("c9131d7e20394f0ea61bdea2b6aea41f")
                    .setClientSecret(clientSecret)
                    .setRefreshToken(refreshToken)
                    .build();
            AuthorizationCodeCredentials refresh = spotifyApi.authorizationCodeRefresh()
                    .build()
                    .execute();
            spotifyApi.setAccessToken(refresh.getAccessToken());
            debug("Fetched token");

            debug("Fetching playlistId=" + playlistId);
            Playlist out = spotifyApi.getPlaylist(playlistId).build().execute();
            debug("Got playlist named=[" + out.getName() + "]");

            int totalTracks = out.getTracks().getTotal();
            List<Integer> tracks = new ArrayList<>();
            for (int i = 0; i < totalTracks; i++) {
                tracks.add(i);
            }
            Collections.shuffle(tracks);

            debug("Shuffling the tracks...");
            for (int newPosition = 0; newPosition < tracks.size(); newPosition++) {
                debug(String.format("Swapping %d and %d", tracks.get(newPosition), newPosition));
                spotifyApi.reorderPlaylistsItems(playlistId, tracks.get(newPosition), newPosition).build().execute();
            }
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new CommandLine(new Cli()).execute(args);
    }
}
