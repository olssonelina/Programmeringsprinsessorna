<?php

/**

* Validering av inloggning

* 

* Validerar inloggning genom att hitta poster i databasen som stÃÂ¤mmer med inloggningsuppgifterna. Om de existerar kollas genom att utfÃÂ¶ra num_rows kommandot pÃÂ¥ de valda posterna och sedan se hur mÃÂ¥nga poster som hittades, dÃÂ¤r 0 = ej hittat.

*

* @param author kevin.solovjov@gmail.com

*/

?>
    <?php

//begÃÂ¤r att encrypt.php och connectDB.php existerar fÃÂ¶r att kyrptera lÃÂ¶senord respektivt skapa en databasanslutning.
require "encrypt.php";
require "connectDB.php";
$response = 0;
$password = $_POST['password'];
//Kallar pÃÂ¥ encrypt funktionen fÃÂ¶r att kryptera lÃÂ¶senordet innan det kan jÃÂ¤mnfÃÂ¶ras med databasen.
$password = encrypt($password);

$username = $_POST['username'];
$con = connect(); //Anropar och ansluter till db.

//VÃÂ¤ljer alla poster dÃÂ¤r lÃÂ¶senorder och anvÃÂ¤ndarnamn matchar inloggningen samt att kontonivÃÂ¥n ÃÂ¤r 1
$selectadmin = mysqli_query($con, "SELECT * FROM account WHERE password = '$password' AND username = '$username' AND level = 1");

//VÃÂ¤ljer alla poster dÃÂ¤r lÃÂ¶senorder och email matchar inloggningen samt att konto nivÃÂ¥n ÃÂ¤r 0
$select = mysqli_query($con, "SELECT * FROM account WHERE password = '$password' AND username = '$username' AND level = 0");
	
//Ser hur mÃÂ¥nga poster man hittat fÃÂ¶r bÃÂ¥da nivÃÂ¥erna	
$nradmin = mysqli_num_rows($selectadmin);
$nr = mysqli_num_rows($select);


//om man hittat en post med den inloggningsinformationen som ÃÂ¤r ett admin account loggas man in som administratÃÂ¶r och skickas tillbaka. 
if($nradmin == 1)
{
$response = -2;
}
//Om man istÃÂ¤llet hittat en post som ej ÃÂ¤r administratÃÂ¶r loggas man in som vanlig anvÃÂ¤ndare.
else if($nr == 1)
{
while($data = mysqli_fetch_array($select)){

$response = $data['accountid'];

}    
}
//Om ingen post hittas fÃÂ¶r vare sig admin eller vanlig anvÃÂ¤ndare skickas ett medellande om misslyckat inloggning.
else{
$response = -1;
}
echo $response;

mysqli_close($con);
?>