<?php
require "connectDB.php";
$con = connect(); //Anropar och ansluter till db.


$quizid = $_POST['quizid'];


echo $quizid;
$sql = "SELECT  *  FROM quizrelation WHERE quizid = '$quizid'";

$checkquiz = mysqli_query($con, $sql);
if (!$checkquiz) {
    printf("Error: %s\n", mysqli_error($con));
    exit();
}    

$results = array();

    while($data = mysqli_fetch_array($checkquiz)){
        
        $results = array();
       
        $questionid = $data['questionid'];    
        
        mysqli_query($con, "DELETE  *  FROM questions WHERE questionid = '$questionid'");
        echo "deleted question";
    
}

$sql = "DELETE  *  FROM quizrelation WHERE quizid = '$quizid'";
mysqli_query($con, $sql);
echo "deleted relation";

$sql = "DELETE  *  FROM quiz WHERE quizid = '$quizid'";
mysqli_query($con, $sql);
echo "deleted quiz";    




    
  mysqli_close($con);
?>