<?php
/**

* Saves friend relations in database

* @param author kevin.solovjov@gmail.com

*/
require "connectDB.php";
$con = connect(); //Connects to Database
$userid = $_POST['userid'];
$friendusername = $_POST['friendusername'];
$request = $_POST['request'];

$response = 0;

//Request == 0 means the database is asked to add a new friendship
if($request == 0){
    
    //Finds the account that matches with the sent username
$select = mysqli_query($con, "SELECT  *  FROM account WHERE username = '$friendusername' ");	
    //Sees if the account exists, available should be "1" if it exists and "0" if it wasn't found.
$available = mysqli_num_rows($select);

    //If the account doesn't exist an error is sent
if($available == 0){
    $response = 1;
}
     //If the account exists it can be added as friend
else if($available == 1){
    
     //Gets the accountID of the friend
    while($data = mysqli_fetch_array($select)){

        $friendid = $data['accountid'];

    }
     //Tries to get existing friendship
    $select = mysqli_query($con, "SELECT  *  FROM friendrelation WHERE accountid = '$userid' AND friendid = '$friendid' ");
    //Sees if the friendship already exists, available should be "1" if it exists and "0" if it wasn't found.
    $available = mysqli_num_rows($select);
    
    //If the friendship wasn't found, it can be created.
    if($available == 0){
    //Adds user ID and the requested friend's ID to the friendrelation table.
        $sql = "INSERT INTO friendrelation (accountid, friendid) VALUES ('$userid','$friendid')"; 
        mysqli_query($con,$sql);
        $response = 3;
    }
    else{
        $response = 2;
    }


   
}


}

//If request == 0, it means the database is asked to return all the friendships of the current user.
else if($request == 1){
    
    //Gets all friendships from the table related to current userID
     $select = mysqli_query($con, "SELECT  *  FROM friendrelation WHERE accountid = '$userid'");
    
if (!$select) {
    printf("Error: %s\n", mysqli_error($con));
    exit();
}    
    
$results = array();
    //Gets the friendID for each friend and adds it to the result array
    while($data = mysqli_fetch_array($select)){
        
       $results[] = $data['friendid']; 

    
}
    $results2  = array();
    //Foreach friendID, gets the account bound to that ID
    foreach ($results as &$value) {
        
        //Gets related account
    $select2 = mysqli_query($con, "SELECT * FROM account WHERE accountid = '$value'");
        if (!$select2) {
    printf("Error: %s\n", mysqli_error($con));
    exit();
}
    while($data2 = mysqli_fetch_array($select2)){
        //Adds all account data to array for each friend
       $results2[] = $data2; 

    
}
}
    //Turns array with all friend accounts into a JSON string
   $response = json_encode($results2);
    
}



//Returns JSON string
 echo $response;
  mysqli_close($con);
?>