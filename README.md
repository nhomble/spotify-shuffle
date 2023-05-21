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

This spotify app isn't actually published. Additionally, the `client_id` is pasted in the source code, and
the `client_secret` isn't shared. To run this yourself, you'll want to create your own spotify app with client/secret.
Additionally, you'll want to configure your callback url to be `http://localhost:8080/callback` (or edit
what `spotify-access` does).