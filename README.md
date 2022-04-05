# _MobileApp - Kotlin_

Aplicația servește drept propriul “cooking coach” sau îți dă șansa să devii chiar tu unul, prin 
adăugarea de noi rețete. Scopul aplicației este de a îngloba cât mai multe rețete 
culinare, care oferă indicații pas cu pas pentru gătirea acestora, prezintă lista de ingrediente, 
menționând și numărul de calorii, alături de timpul de preparare.

## Funcționalități
- Prezintă toate operațiile CRUD, fiecare dintre acestea fiind exemplificate într-un ecran separat. 
- Rețele afișate sunt preluate de pe un server, iar la realizarea oricărei operații, modificările 
vor fi prezente și pe server.
- Există implementare și pentru salvarea datelor într-o baza de date locală (pe lângă server + salvarea în memorie).

## Tehnologii

- Limbaj de programare: Kotlin 
- Bază de date: PostgreSQL
- Server: Django

## Rulare
- _Partea de server:_

Se va crea un superuser cu ajutorul comenzii: 
```
$ python manage.py createsuperuser
```

După care se rulează comenzile:
```
$ python manage.py makemigrations
$ python manage.py migrate
$ python manage.py runserver adresaIP:port
```

Pentru mai multe detalii, urmariti tutorialul: https://docs.djangoproject.com/en/4.0/intro/tutorial02/

- _Partea de aplicație:_  
Se actualizează adresa IP și portul în cadrul proiectului în fișierul "AppModule" din pachetul "di" 
(app\src\main\java\com\example\captaincook\di). Aceeași adresă IP, ălaturi de port se va trece și în 
fișierul network_security_config.xml din folderul resources (app\src\main\res\xml).

## DEMO al aplicației
În momentul deschiderii aplicației, este afișat ecranul de _Discover_, unde sunt prezente toate rețetele 
din baza de date (partea de feed).
<p align="center">
     <img src = "imagini_readme\1.PNG" height="300" width="150" style="float:left">
</p>

La click pe una dintre rețete, se pot vizualiza detaliile despre aceasta.
<p>
<div align="center" >
    <img src = "imagini_readme\2_1.PNG" height="300" width="150" style="float:left">
    <img src = "imagini_readme\2_2.PNG" height="200" width="150" style="float:left">
</div>
</p>

Dacă intrăm în secțiunea _My Recipes_, pe ecran vor fi afișate rețetele adăugate de către noi. În acest 
ecran putem alege mai multe opțiuni: să adăugăm o rețeta nouă, sau să edităm, respectiv ștergem una 
existentă.
<p align="center">
     <img src = "imagini_readme\3.png" height="300" width="150" style="float:left">
</p>

La acționarea butonului pentru adăugarea unei noi rețete, se va deschide un ecran nou cu toate 
câmpurile corespunzătoare unei rețete care trebuie completate.
<p align="center">
     <img src = "imagini_readme\4.PNG" height="300" width="150" style="float:left">
</p>

Pentru editarea unei rețete, se va deschide un ecran identic celui de ADD, însa, de data aceasta, 
cu toate câmpurile deja completate, având posibilitatea de a le modifica.

