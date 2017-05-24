<?php
require "connectDB.php";
$con = connect(); //Anropar och ansluter till db.


$accountid = $_POST['accountid'];
$friendid = $_POST['friendid'];

$sql = "DELETE FROM friendrelation WHERE accountid = '$accountid' AND friendid = '$friendid'";
mysqli_query($con, $sql);

echo "0";    




    
  mysqli_close($con);
?>