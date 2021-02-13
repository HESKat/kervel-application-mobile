<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    
    include_once '../config/database.php';
    include_once '../classe/users.php';

    $database = new Database();
    $db = $database->getConnection();
    echo' h everyone ';

    echo' avant';
    $users = new Users($db);
    echo' izann ';
    $stmt = $users->getUsers();
    echo ' apres';
    $count = $stmt->rowCount();
               
                    echo "\n";
                    print "Il y a " .  $count . " ligne(s) correspondante(s).";
                    echo "\n";
                    echo json_encode($count);
      
                    
                    if($count > 0){
        
                    $userArr = array();
                    $userArr["body"] = array();
                    $userArr["count"] = $count;

                      while ($row = $stmt->fetch()){
                        extract($row);
                        $e = array(
                            "id" => $id,
                            "name" => $username,
                            "email" => $email,
                            "signup_date" => $signup_date,
                            "password" => $password,
                            "user_type" => $user_type,
                            "last_name" => $last_name,
                            "first_name" => $first_name,
                            "share_public" => $share_public


                        );

                        array_push($userArr["body"], $e);
                   }
                   echo json_encode($userArr);
                } 


    echo 'fin';
?>