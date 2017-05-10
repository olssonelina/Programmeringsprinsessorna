<?php
require "connectDB.php";
$con = connect(); //Anropar och ansluter till db.

$finish = $_POST['finish'];


if($finish == 0){
$description = $_POST['description'];
$option1 = $_POST['option1'];
$option2 = $_POST['option2'];
$option3 = $_POST['option3'];
$option4 = $_POST['option4'];
$longitude = $_POST['longitude'];
$latitude = $_POST['latitude'];
$correctanswer = $_POST['correctanswer'];

$sql = "INSERT INTO questions (description, option1, option2, option3, option4, longitude, latitude, correctanswer) VALUES ('$description', '$option1', '$option2', '$option3', '$option4', '$longitude', '$latitude', '$correctanswer')";

mysqli_query($con,$sql);

$savedquestion = mysqli_query($con, "SELECT  *  FROM questions WHERE description = '$description' AND longitude = '$longitude' AND latitude = '$latitude' ");		

while($data = mysqli_fetch_array($savedquestion)){

echo $data['questionid'];

}
}

else if($finish == 1){

$quizdescription = $_POST['quizdescription'];
$quiztitle = $_POST['title'];
$questionidarray = $_POST['questionidarray'];
$userid = $_POST['userid'];

    
    
$checktaken = mysqli_query($con, "SELECT  *  FROM quiz WHERE title = '$quiztitle'");
$nr = mysqli_num_rows($checktaken);
    
if($nr == 0){
    
    
$sql = "INSERT INTO quiz (description, title) VALUES ('$quizdescription', '$quiztitle')";

mysqli_query($con,$sql);

$savedquiz = mysqli_query($con, "SELECT  *  FROM quiz WHERE description = '$quizdescription' AND title = '$quiztitle'");	

while($data = mysqli_fetch_array($savedquiz)){

$quizid = $data['quizid'];

}
    
    $jsonArrayQuestionId = json_decode($questionidarray, TRUE);
    $jsonArrayLength = count($jsonArrayQuestionId);
    
    for ($x = 0; $x <  $jsonArrayLength; $x++) {
        
     $sql = "INSERT INTO quizrelation (quizid, questionid) VALUES ('$quizid', '$jsonArrayQuestionId[$x]')";

        mysqli_query($con,$sql);
        
}
    $sql = "INSERT INTO accountrelation (quizid, accountid) VALUES ('$quizid', '$userid')";
mysqli_query($con,$sql);
    
    
echo '0';
}
else{
echo '-1';    
}
    
}
    
  mysqli_close($con);
?>