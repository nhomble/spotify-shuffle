# spotify-shuffle

actually shuffle the (gym) playlists

## Usage

### spotify-shuffle

This is the cli that is meant to be called locally or with your cron.

### spotify-access

Stupid helper cli/webserver that will do the authorization code flow and print your `refresh_token`. Once you have that
token, you can just pipe that into `spotify-shuffle`. The shuffle cli handles fetching a fresh `access_token`.

This helper assumes the environment variable `SPOTIFY_CLIENT_SECRET` is set.

## Setup

This Spotify app isn't actually published. Additionally, the `client_id` is pasted in the source code, and
the `client_secret` isn't shared. To run this yourself, you'll want to create your own Spotify app with client/secret.
Additionally, you'll want to configure your callback URL to be `http://localhost:8080/callback` (or edit
what `spotify-access` does).

### GitHub Actions

Action secrets are setup for the `client_secret` and `refresh_token` to be fed into the cli.

### playlist id

The easiest thing is to get the share URL for your playlist. Given a URL like `https://open.spotify.com/playlist/{{playlistId}?}si={{si}}`, extract `playlistId` and pass it to the `-p` argument of the cli.
