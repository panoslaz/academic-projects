<?php
require_once(dirname(__FILE__).'/../helpers/DbConnect.php');

class PostRepository
{

    private $conn;

    function __construct()
    {
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    function getPostsForTopic($topicId) {

        $stmt = $this->conn->prepare("SELECT * FROM n2u_forum_post WHERE topic_id=? ORDER BY date_created DESC");
        $stmt->bind_param("i", $topicId);
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

    function addPost($text, $userId, $topicId) {

        $stmt = $this->conn->prepare("INSERT INTO n2u_forum_post(text, user_id, topic_id, date_created) values(?, ?, ?, NOW())");
        $stmt->bind_param("sii", $text, $userId, $topicId);

        $stmt->execute();

        $stmt->close();
        return "Success ";
    }
    

}