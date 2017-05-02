<?php

/**

* Validering av inloggning

* 

* Validerar inloggning och håller koll på felaktiga inloggningar genom att försöka hitta poster i databasen som stämmer med inloggningsuppgifterna. Om de existerar kollas genom att utföra num_rows kommandot på de valda posterna och sedan se hur många poster som hittades, där 0 = ej hittat.

De felaktiga inloggningarna kollas istället genom att skapa en session och uppdatera denna för varje misslyckad inloggning.
*

* @param author kevin.solovjov@itggot.se

*/

?>
    <?php

//begär att encrypt.php och connectDB.php existerar för att kyrptera lösenord respektivt skapa en databasanslutning.
require "encrypt.php";
require "connectDB.php";
$response = 0;
$password = $_POST['password'];
//Kallar på encrypt funktionen för att kryptera lösenordet innan det kan jämnföras med databasen.
$password = encrypt($password);

$username = $_POST['username'];
$con = connect(); //Anropar och ansluter till db.

//Väljer alla poster där lösenorder och användarnamn matchar inloggningen samt att kontonivån är 1
$selectadmin = mysqli_query($con, "SELECT * FROM account WHERE password = '$password' AND username = '$username' AND level = 1");

//Väljer alla poster där lösenorder och email matchar inloggningen samt att konto nivån är 0
$select = mysqli_query($con, "SELECT * FROM account WHERE password = '$password' AND username = '$username' AND level = 0");
	
//Ser hur många poster man hittat för båda nivåerna	
$nradmin = mysqli_num_rows($selectadmin);
$nr = mysqli_num_rows($select);


//om man hittat en post med den inloggningsinformationen som är ett admin account loggas man in som administratör och skickas tillbaka.  Misslyckade lög-in försök resettas. 
if($nradmin == 1)
{
$response = 1;
}
//Om man istället hittat en post som ej är administratör loggas man in som vanlig användare. Namn och personid sparas i sessionen och misslyckade lög-in försök resettas.
else if($nr == 1)
{
$response = 2;
}
//Om ingen post hittas för vare sig admin eller vanlig användare skickas ett medellande om misslyckat försök och antalet försök ökar med ett.
else{
$response = 3;
}
echo $response;

mysqli_close($con);
?>