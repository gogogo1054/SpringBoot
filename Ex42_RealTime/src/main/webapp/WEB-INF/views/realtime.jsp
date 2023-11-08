<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Firebase Realtime</title>
<script type="http://code.jquery.com/jquery.js"></script>

<script type="module">
import { initializeApp } from 'https://www.gstatic.com/firebasejs/10.4.0/firebase-app.js'
import { getDatabase, ref, onValue, set, child, push, onChildAdded, query, limitToLast } 
	from 'https://www.gstatic.com/firebasejs/10.4.0/firebase-database.js'

const firebaseConfig = {
  apiKey: "AIzaSyBSoaHX6ltSMShK6qCkWX7IjBIAmplITWw",
  authDomain: "final-project-54389.firebaseapp.com",
  databaseURL: "https://final-project-54389-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "final-project-54389",
  storageBucket: "final-project-54389.appspot.com",
  messagingSenderId: "999565386417",
  appId: "1:999565386417:web:f5a3dc6d5a4a7e241a3890"
};
	
var app = initializeApp(firebaseConfig);
var db = getDatabase(app);

var roomName;
var userName;

function connect() {
	roomName = $('#roomName').val();
	userName = $('#userName').val();

	var dbRef = ref(db, 'chat/' + roomName);
	var lastDbRef = query(dbRef, limitToLast(1));
/*
	// 데이터 변경시 모든 데이터 조회됨.
	onValue(dbRef, (snapshot) => {
		snapshot.forEach((childSnapshot) => {
		// var key = childSnapshot.key;
		var data = childSnapshot.val();

		var name = data.userName;
		var msg = data.message;

		console.log("[2]" + name + " : " + msg);
		appendMessage(name + " : " + msg);
		});
	});
*/
	// dbRef : 처음에는 기존 데이터 모두 조회됨. 이후 추가된 데이터만 조회됨.
	// lastDbRef : 마지막 데이터 하나 조회
	onChildAdded(lastDbRef, (data) => {
		var name = data.val().userName;
		var msg = data.val().message;

		console.log("[3]" + name + " : " + msg);
		appendMessage(name + " : " + msg);
	});
}

function writeNewPost(roomId, name, msg) {
	var postData = {
		userName: name,
		message: msg
	};

	// Get a key for a new Post	
	var newPostKey = push(child(ref(db), 'chat/' + roomId)).key;
	var newRef = ref(db, 'chat/' + roomId + '/' + newPostKey);

	set(newRef, postData);
}

function send() {
	var msg = $('#message').val();
	writeNewPost(roomName, userName, msg);
}

function appendMessage(msg) {
	$('#chatMessageArea').append(msg + "<br>");
	var chatAreaHeight = $('#chatArea').height();
	var maxScroll = $('#chatMessageArea').height() - chatAreaHeight;
	$('#chatArea').scrollTop(maxScroll);
}

$(docment).ready(function() {
	console.log(111);
	$(#sendBtn').click(function() { send(); });
	$('#enterBtn').click(function() { connect(); });
});
</script>
<style>
#chatArea{
	width: 200px; height: 100px; overflow-y: auto; boder: 1px solid black;
}
</style>
</head>
<body>
	Room Name : <input type="text" id="roomName"><br>
	User Name : <input type="text" id="userName"><br>
	<input type="button" id="enterBtn" value="입장">
	
	<h1>대화 영역</h1>
	<div id="chatArea"><div id="chatMessageArea"></div></div>
	<br>
	
	<input type="text" id="message"><br>
	<input type="button" id="sendBtn" value="전송">
</body>
</html>