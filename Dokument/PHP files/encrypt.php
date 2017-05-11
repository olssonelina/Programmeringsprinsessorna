<?php

/**

* Kryptering

* 

* Funktionen för kryptering av lösenord med hjälp av sha1 och crypt funktionerna
*

* @param author kevin.solovjov@itggot.se

*/

//Först krypteras order "Nightingale" med sha1, och sedan använder crypt metoden detta krypterade ord för att kryptera lösenorder.
function encrypt($password){
	$encrypt = sha1("Nightingale");
	$password = crypt($password,$encrypt);
	return $password;
}
?>