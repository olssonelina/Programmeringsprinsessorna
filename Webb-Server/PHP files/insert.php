<?php
/**

* Saves accounts in database

* @param author kevin.solovjov@gmail.com

*/
require "connectDB.php";
require "encrypt.php";
$con = connect(); //Connects to Database
$username = $_POST['username'];
$username = mysqli_real_escape_string($con, $username);
$username = strtolower($username);
$password = $_POST['password'];
$password = mysqli_real_escape_string($con, $password);
//Encrypts password before sending it to database for security reasons.
$password = encrypt($password);
$regdate = date("Y-m-d H:i:s");
$error = 0;

$sql = "INSERT INTO account (username, regdate, password) VALUES ('$username','$regdate','$password')";

$check = $_POST['username'];	
			//Gets all rows in table where username matches the one sent by the application
$verify = mysqli_query($con, "SELECT  *  FROM account WHERE username = '$check' ");	
//Counts how many results were found, should be 0 if username isn't taken
$available = mysqli_num_rows($verify);

if($available == 0 && isset($_POST['username']) && !empty($_POST['username']) && isset($_POST['password']) && !empty($_POST['password'])){
mysqli_query($con,$sql);
}
//Error if Username is taken
else if($available == !0){
$error = 1;    
}
//Checks if username was filled
else if(!isset($_POST['username']) or empty($_POST['username'])){
$error = 2; 
}
//Checks if password was filled
else if(!isset($_POST['password']) or empty($_POST['password'])){
$error = 3; 
}

//Sends result back to application
echo $error;

  mysqli_close($con);
?>