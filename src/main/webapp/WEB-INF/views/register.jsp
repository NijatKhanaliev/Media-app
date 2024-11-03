<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f4f4f9;
        }

        #registerForm {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            color: #333;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            margin: 5px 0 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div id="registrationForm">
        <h1>Register</h1>
        <form>
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" />
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" />

            <button type="button" onclick="register()">register</button>
        </form>
    </div>

    <script>
        function register() {
            const dataForms = {
                email: document.getElementById("email").value,
                password: document.getElementById("password").value
            }

            fetch('/register', {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dataForms)
            })
                .then(res => {
                    if (res.ok) {
                        window.location.href = "/login"
                    } else {
                        console.log("Registration failed")
                    }
                })
                .catch(err => console.log("Error", err))
        }

    </script>

</body>
</html>
