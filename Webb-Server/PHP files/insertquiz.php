<?php
require "connectDB.php";
$con = connect(); //Anropar och ansluter till db.
$finish = $_POST['finish'];


if($finish == 0){
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

    if($questionid == -1){
$sql = "INSERT INTO questions (description, option1, option2, option3, option4, longitude, latitude, correctanswer, questiontype) VALUES ('$description', '$option1', '$option2', '$option3', '$option4', '$longitude', '$latitude', '$correctanswer', '$questiontype')";
    

mysqli_query($con,$sql);
        
$savedquestion = mysqli_query($con, "SELECT * FROM questions ORDER BY questionid DESC LIMIT 0,1");		

while($data = mysqli_fetch_array($savedquestion)){

echo $data['questionid'];
}
    }
else{
        $sql = "UPDATE questions SET description='$description', option1 = '$option1', option2 = '$option2', option3 = '$option3', option4 = '$option4', longitude = '$longitude', latitude = '$latitude', correctanswer = '$correctanswer', questiontype = '$questiontype' WHERE questionid='$questionid'";
    
    mysqli_query($con,$sql);

    echo -3;
    }

}


else if($finish == 1){
$questionid = $_POST['questionid']; 
$quizid = $_POST['quizid'];
$quizdescription = $_POST['quizdescription'];
$quiztitle = $_POST['title'];
$questionidarray = $_POST['questionidarray'];
$userid = $_POST['userid'];

    
if($quizid == -1){
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
    else{
        $sql = "UPDATE quiz SET description='$quizdescription', title = '$quiztitle' WHERE quizid='$quizid'";
mysqli_query($con,$sql);
        
        
        
            $jsonArrayQuestionId = json_decode($questionidarray, TRUE);
            $jsonArrayLength = count($jsonArrayQuestionId);
    
            for ($x = 0; $x <  $jsonArrayLength; $x++) {
        
                $sql = "INSERT INTO quizrelation (quizid, questionid) VALUES ('$quizid', '$jsonArrayQuestionId[$x] > 0')";
if($jsonArrayQuestionId[$x] > 0){
    

        

        mysqli_query($con,$sql);
    }
        }
        
        echo '-2';
    
}
}

    
  mysqli_close($con);
?>