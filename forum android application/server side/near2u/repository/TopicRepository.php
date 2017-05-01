<?php
require_once(dirname(__FILE__).'/../helpers/DbConnect.php');

class TopicRepository
{

    private $conn;

    function __construct()
    {
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    /**
     * get topics for a forum
     */
    function getTopicsForForum($forumId) {

        $stmt = $this->conn->prepare("SELECT * FROM n2u_forum_topic WHERE forum_id=? ORDER BY date_created DESC");
        $stmt->bind_param("i", $forumId);
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

    /**
     * create a new topic
     */
    function addTopic($title, $text, $userId, $forumId) {

        $stmt = $this->conn->prepare("INSERT INTO n2u_forum_topic(title, text, user_id, forum_id, date_created) values(?, ?, ?, ?, NOW())");
        $stmt->bind_param("ssii",$title, $text, $userId, $forumId);

        $stmt->execute();

        $stmt->close();
        return "Success ";
    }
    

}