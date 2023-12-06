# math_game
Agilni pristup u razvoju softvera
- Arkadna igra za Android uređaja

# Score sistem
Skor sistem je baziran na REST Api i implenetira dve rute <br/>

 GET `/api/scores/` - povlači sve rezultate iz baze podataka
 ```json
  {
    "status": 200,
    "status_message": "Data found",
    "data": [
        {
            "score_id": 1,
            "username": "testUser",
            "score": 500,
            "date": "2023-12-06"
        },
        {
            "score_id": 2,
            "username": "another-user",
            "score": 734,
            "date": "2023-12-06"
        },
        {
            "score_id": 4,
            "username": "newUser",
            "score": 635,
            "date": "2023-12-06"
        }
    ]
}
```
> Ukoliko ne postoji ni jedan zapis u bazi podataka, korisniku se vraca odgovarajuca poruka <br/>
 
 POST `/api/scores/` - upisuje novi rezultat u bazu podataka

 ```json
  {
    "username" : "newUser",
    "score" : "635"
  }
 ```
> Kao ulazne podatke, funkcija prima `username` tipa string i `score` tipa int <br/>
> Ukoliko nije poslat validan json format, vraca se odgovarajuca poruka o gresci
