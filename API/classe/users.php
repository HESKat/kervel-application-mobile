<?php
//echo'debut Users ';
    class Users{

       // Connection
        private $conn;
        private $db_table = "users";

        // Db connection
        public function __construct($db){
            include_once '../config/constants.php';
            $this->conn = $db;
          //  echo" succes in users";
        }

       
        // GET ALL
       public function getUsers(){

           $stmt = "SELECT * FROM users WHERE last_name = 'katia'";
           
            try {
                echo 'funct';
             $stmt = $this->conn->query($stmt);
                 
            return $stmt;
                 
            }catch (PDOException $e) {
            echo "Error : " . $e->getMessage() . "<br/>";
            //die();
            }
         
           
       
    }
         
        public function insertEvent($date, $type, $parameter, $id_parcelle){
            $date = $date;
            $type = $type;
            $parameter = $parameter;
            $id_parcelle = $id_parcelle;
            
             $stmt = "
            INSERT INTO evenements
                (date, type, parameter, id_parcelle)
            VALUES
            ('".$date."','" . $type . "','".$parameter."','" . $id_parcelle . "')";
            
            //(null,'type','par',200)";
            
            try {
                
                $stmt = $this->conn->prepare($stmt);
                
                if ($stmt->execute()) {
                return true;
            
            }     
            } catch (PDOException $e) {
             echo "Error : " . $e->getMessage() . "<br/>";
              }
          }
        
        public function login($email, $password){
           // echo 'loginn '
            $getEmail = $email;
            //echo $getEmail ;
           // echo " mot de passe est : " .$password;
            $stmt = "SELECT * FROM users WHERE email='".$getEmail."'";

            
        try{
            $stmt = $this->conn->prepare($stmt);
            $stmt->execute();
            
            $count = $stmt->rowCount();
           // echo $count;
           // echo" apres rowcount";
            
        if ($row = $stmt->fetch()) {
           // echo ' after fetch ';
            // verifying user password
            $dbemail = $row['email'];
            $dbpassword = $row['password'];
            $dbusertype = $row['user_type'];
            //echo $dbpassword;
    
            if ($dbemail == $email && $password == $dbpassword ) {
                //$login = true;
                return $row;
            } else return false;
            
            return $row;
         } else{
            return null;
        }  

        } catch (PDOException $e) {
             echo "Error : " . $e->getMessage() . "<br/>";
        }
       
        }
            
      
}
?>