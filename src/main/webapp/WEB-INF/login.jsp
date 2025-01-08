<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - PTLinker</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #ffffff;
        }

        .header {
            background-color: #ff6600;
            color: white;
            padding: 10px;
            text-align: left;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .header img {
            width: 30px;
            height: 30px;
            margin-right: 10px;
        }

        .header h1 {
            margin: 0;
            font-size: 20px;
        }

        .container {
            text-align: center;
            padding: 20px;
            width: 90%;
            margin: 0 auto;
        }

        .container h2 {
            font-family: 'Courier New', Courier, monospace;
            font-size: 36px;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 20px;
            text-align: left;
            max-width: 400px;
            margin: 10px auto;
        }

        .form-group label {
            display: block;
            font-size: 18px;
            margin-bottom: 5px;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #000;
            border-radius: 5px;
        }

        .btn {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            color: white;
            background-color: #000;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            margin: 10px 5px;
        }

        .btn:hover {
            opacity: 0.9;
        }

        .btn-secondary {
            background-color: #333;
        }

        /* Media Queries per rendere la pagina responsive */
        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                align-items: center;
            }

            .header h1 {
                font-size: 18px;
                text-align: center;
            }

            .container h2 {
                font-size: 28px;
            }

            .form-group input {
                font-size: 14px;
                padding: 8px;
            }

            .btn {
                font-size: 14px;
                padding: 8px 15px;
            }
        }

        @media (max-width: 480px) {
            .header h1 {
                font-size: 16px;
            }

            .container h2 {
                font-size: 24px;
            }

            .form-group label {
                font-size: 16px;
            }

            .form-group input {
                font-size: 14px;
                padding: 6px;
            }

            .btn {
                font-size: 14px;
                padding: 8px 12px;
            }
        }
    </style>
</head>
<body>
<div class="header">
    <h1>PT-Linker</h1>
</div>

<div class="container">
    <h2>LOGIN</h2>
    <form action="LoginServlet" method="post">
        <div class="form-group">
            <label for="email">E-Mail</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="btn">LOG</button>
    </form>
    <a href="RegistrazioneServlet" class="btn btn-secondary">Registrazione</a>
</div>
</body>
</html>
