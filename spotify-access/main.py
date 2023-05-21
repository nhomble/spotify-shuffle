import os
import webbrowser
import requests
from flask import Flask, request
import base64

if __name__ == '__main__':
    app = Flask(__name__)
    browser = webbrowser.open(f"file://{os.path.realpath('login.html')}")
    client_secret = os.getenv('SPOTIFY_CLIENT_SECRET')

    @app.route('/callback')
    def callback():
        creds = f"c9131d7e20394f0ea61bdea2b6aea41f:{client_secret}".encode('utf-8')
        auth = requests.post("https://accounts.spotify.com/api/token", headers={
            'Authorization': f"Basic {base64.b64encode(creds).decode('ascii')}"
        }, data={
            "code": request.args['code'],
            "redirect_uri": "http://localhost:8080/callback",
            "grant_type": "authorization_code"
        }, json=True)
        out = auth.json()
        print(f"refresh_token={out['refresh_token']}")
        print(f"access_token={out['access_token']}")
        print(auth)

    app.run(port=8080)
