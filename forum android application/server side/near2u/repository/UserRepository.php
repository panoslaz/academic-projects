<?php
require_once(dirname(__FILE__).'/../helpers/DbConnect.php');

class UserRepository
{

    private $conn;

    function __construct()
    {

        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    /**
     * get the messages that a user is a recipient or a sender
     */
    function getHistoryForUser($userId) {

        $stmt = $this->conn->prepare("SELECT * FROM n2u_message INNER JOIN n2u_message_to_receivers
ON n2u_message.id=n2u_message_to_receivers.message_id INNER JOIN n2u_user ON n2u_message_to_receivers.receiver_id=n2u_user.id
where from_user_id=? or receiver_id=? order by DATE_SENT DESC ");
        $stmt->bind_param("ii", $userId, $userId);
        $stmt->execute();
        $result = $stmt->get_result();
        $messages = array();
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                array_push($messages,$row);
            }
        }
        $stmt->close();
        return $messages;

    }

    /**
     * get locations of a user
     */
    function getLocationHistoryForUser($userId) {

        $stmt = $this->conn->prepare("SELECT * FROM n2u_user_history where user_id=? order by date_visited DESC");
        $stmt->bind_param("i", $userId);
        $stmt->execute();
        $result = $stmt->get_result();
        $messages = array();
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                array_push($messages,$row);
            }
        }
        $stmt->close();
        return $messages;

    }

    /**
     * create location - user entry
     */
    function addLocationForUser($userId, $longitude, $latitude) {

        $stmt = $this->conn->prepare("INSERT INTO n2u_user_history(user_id, longitude, latitude, date_visited) values(?, ?, ?, NOW())");
        $stmt->bind_param("idd", $userId, $longitude, $latitude);

        $stmt->execute();

        $stmt->close();
        return "Success ";
    }

    function authenticateUser($username, $password) {

        $stmt = $this->conn->prepare("SELECT * FROM n2u_user where username=? and password=?");
        $stmt->bind_param("ss", $username, $password);

        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            return $result->fetch_assoc();
        }
        $stmt->close();
        return false;
    }

    function getUserInfo($username, $password) {

        $stmt = $this->conn->prepare("SELECT * FROM n2u_user where username=? and password=?");
        $stmt->bind_param("ss", $username, $password);

        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            return $result->fetch_assoc();
        }
        $stmt->close();
        return false;
    }

    function getUserRecentLocations() {

        $stmt = $this->conn->prepare("SELECT tt.*, u.username FROM n2u_user_history tt INNER JOIN
(SELECT user_id, MAX(date_visited) AS MaxDateTime FROM n2u_user_history GROUP BY user_id) groupedtt ON tt.user_id = groupedtt.user_id
AND tt.date_visited = groupedtt.MaxDateTime JOIN n2u_user u ON tt.user_id = u.id");
        $stmt->execute();
        $result = $stmt->get_result();
        $userLocations = array();
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                array_push($userLocations, $row);
            }
        }
        $stmt->close();
        return $userLocations;

    }

    function getUserDetails($username) {

        $stmt = $this->conn->prepare("select * from n2u_user where username=?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $result = $stmt->get_result();
        $userLocations = array();
        if ($result->num_rows > 0) {
            return $result->fetch_assoc();
        }
        $stmt->close();
        return false;
    }

    function getUsersApartCurrent($userId) {

        $stmt = $this->conn->prepare("select id from n2u_user where id!=?");
        $stmt->bind_param("i", $userId);

        $stmt->execute();
        $result = $stmt->get_result();
        $userIds = array();
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                array_push($userIds, $row);
            }
        }
        $stmt->close();
        return $userIds;
    }

    function addUser($username, $password, $occupation, $birth_date, $hobbies, $interests) {

        $stmt = $this->conn->prepare("INSERT INTO n2u_user(username, password, occupation, birth_date, hobbies, interests) VALUES (?,?,?,?,?,?)");
        $stmt->bind_param("ssssss", $username, $password, $occupation, $birth_date, $hobbies, $interests);

        $stmt->execute();

        $stmt->close();
        return "Success ";
    }

}