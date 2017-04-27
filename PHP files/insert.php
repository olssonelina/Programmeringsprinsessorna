<?php
require "connectDB.php";
require "encrypt.php";
$con = connect(); //Anropar och ansluter till db.
$username = $_POST['username'];
$username = mysqli_real_escape_string($con, $username);
$password = $_POST['password'];
$password = mysqli_real_escape_string($con, $password);
$password = encrypt($password);
$regdate = date("Y-m-d H:i:s");
$error = 0;

$sql = "INSERT INTO account (username, regdate, password) VALUES ('$username','$regdate','$password')";

$check = $_POST['username'];	
			//VÃÂ¤ljer alla i tabellen dÃÂ¤r anvÃÂ¤rdarnamnet ÃÂ¤r samma som det skicakde i formulÃÂ¤ret	
$verify = mysqli_query($con, "SELECT  *  FROM account WHERE username = '$check' ");	
$available = mysqli_num_rows($verify);

if($available == 0 && isset($_POST['username']) && !empty($_POST['username']) && isset($_POST['password']) && !empty($_POST['password'])){
mysqli_query($con,$sql);
}
else if($available == !0){
$error = 1;    
}
else if(!isset($_POST['username']) or empty($_POST['username'])){
$error = 2; 
}
else if(!isset($_POST['password']) or empty($_POST['password'])){
$error = 3; 
}

echo $error;

  mysqli_close($con);
?>