<?php

/**

* Encryption

* 

* Function for encrypting passwords
*

* @param author kevin.solovjov@itggot.se

*/

function encrypt($password){
	$encrypt = sha1("Nightingale");
	$password = crypt($password,$encrypt);
	return $password;
}
?>