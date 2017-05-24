<?php
require "connectDB.php";
$con = connect(); //Anropar och ansluter till db.

$request = $_POST['request'];


if($request == 0){
$questionid = $_POST['questionid'];
    
    
$description = $_POST['description'];
$option1 = $_POST['option1'];
$option2 = $_POST['option2'];
$option3 = $_POST['option3'];
$option4 = $_POST['option4'];
$questiontype = $_POST['questiontype'];
$longitude = $_POST['longitude'];
$latitude = $_POST['latitude'];
$correctanswer = $_POST['correctanswer'];

$sql = "UPDATE questions SET description='$description', option1 = '$option1', option2 = '$option2', option3 = '$option3', option4 = '$option4', longitude = '$longitude', latitude = '$latitude', correctanswer = '$correctanswer', questiontype = '$questiontype',  WHERE questionid='$questionid'";
    
    echo "0";    
}
else if($request == 1){
$quizid = $_POST['quizid'];

$quizdescription = $_POST['quizdescription'];
$quiztitle = $_POST['title'];
    
$sql = "UPDATE quiz SET description='$quizdescription', title = '$quiztitle',  WHERE quizid='$quizid'";

mysqli_query($con, $sql);



echo "1";    



}
    
  mysqli_close($con);
?>