<?php
/**

* Deletes friendship relations from database

* @param author kevin.solovjov@gmail.com

*/
require "connectDB.php";
$con = connect(); //Anropar och ansluter till db.


$accountid = $_POST['accountid'];
$friendid = $_POST['friendid'];

//Deletes the friend from the relation table.
$sql = "DELETE FROM friendrelation WHERE accountid = '$accountid' AND friendid = '$friendid'";
mysqli_query($con, $sql);

echo "0";    




    
  mysqli_close($con);
?>