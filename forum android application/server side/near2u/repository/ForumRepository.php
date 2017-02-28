<?php
require_once(dirname(__FILE__).'/../helpers/DbConnect.php');

class ForumRepository
{

    private $conn;

    function __construct()
    {
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    function getForums() {

        $stmt = $this->conn->prepare("SELECT * FROM n2u_forum_category");
        $stmt->execute();
        $result = $stmt->get_result();
        $forums = array();
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                array_push($forums, $row);
            }
        }
        $stmt->close();
        return $forums;
    }


}