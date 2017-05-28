<?php
/**

* Deletes questions

* @param author kevin.solovjov@gmail.com

*/
require "connectDB.php";
$con = connect(); //Connects to Database


$questionid = $_POST['questionid'];


//Deletes the question
$sql = "DELETE FROM questions WHERE questionid = '$questionid'";
mysqli_query($con, $sql);

echo "0";    




    
  mysqli_close($con);
?>