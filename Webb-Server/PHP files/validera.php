<?php

/**

* Validation of login

* 

* Validates login by comparing send parameters to existing accounts in database

*

* @param author kevin.solovjov@gmail.com

*/

?>
    <?php

require "encrypt.php";
require "connectDB.php";
$response = 0;
$password = $_POST['password'];
//Encrypts the password before comparing to database, since the saved passwords are already encrypted.
$password = encrypt($password);

$username = $_POST['username'];
$username = strtolower($username);
$con = connect(); //Connects to database

//Finds all accounts that match the sent login credentials and are administrators
$selectadmin = mysqli_query($con, "SELECT * FROM account WHERE password = '$password' AND username = '$username' AND level = 1");

//Finds all accounts that match the sent login credentials and are normal users
$select = mysqli_query($con, "SELECT * FROM account WHERE password = '$password' AND username = '$username' AND level = 0");
	
//Counts matches for both searches, if both are 0 no account with those credentials exists, otherwise one will be "1" depending on the account level
$nradmin = mysqli_num_rows($selectadmin);
$nr = mysqli_num_rows($select);


//If an admin account was found, a special code is sent back to the application
if($nradmin == 1)
{
$response = -2;
}
//If the found account is a normal user, the account ID is sent back instead to be used by the application
else if($nr == 1)
{
while($data = mysqli_fetch_array($select)){

$response = $data['accountid'];

}    
}
//If no account was found with the chosen credentials, the application is notified with a special code
else{
$response = -1;
}

//Returns response to application
echo $response;

mysqli_close($con);
?>