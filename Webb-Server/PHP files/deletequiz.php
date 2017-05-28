<?php
/**

* Deletes quizzes from database

* @param author kevin.solovjov@gmail.com

*/
require "connectDB.php";
$con = connect(); //Anropar och ansluter till db.


$quizid = $_POST['quizid'];


//Finds all questions beloning to the quiz
$sql = "SELECT * FROM quizrelation WHERE quizid = '$quizid'";

$checkquiz = mysqli_query($con, $sql);
if (!$checkquiz) {
    printf("Error: %s\n", mysqli_error($con));
    exit();
}    

$results = array();

//For every question found, removes the question from the questions table by identifying ID
    while($data = mysqli_fetch_array($checkquiz)){
        
        $results = array();
       
        $questionid = $data['questionid'];    
        
        
        mysqli_query($con, "DELETE FROM questions WHERE questionid = '$questionid'");

    
}

//Removes all question relations related to the quizID
$sql = "DELETE FROM quizrelation WHERE quizid = '$quizid'";
mysqli_query($con, $sql);

//Removes all account relations related to the quizID
$sql = "DELETE FROM accountrelation WHERE quizid = '$quizid'";
mysqli_query($con, $sql);
//Deletes Quiz
$sql = "DELETE FROM quiz WHERE quizid = '$quizid'";
mysqli_query($con, $sql);

echo "0";    




    
  mysqli_close($con);
?>