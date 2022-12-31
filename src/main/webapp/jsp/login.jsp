<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="username" type="java.lang.String"--%>
<%--@elvariable id="INVALID_LOGIN" type="java.lang.String"--%>
<%--@elvariable id="INVALID_PASSWORD" type="java.lang.String"--%>
<%--@elvariable id="USER_BLOCKED" type="java.lang.String"--%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>University Admissions | Вхід</title>
    <link href="favicon.ico" rel="icon" type="image/x-icon"/>
    <link href="https://unpkg.com/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="flex min-h-screen flex-col bg-gray-100">
    <div class="container mx-auto flex max-w-lg flex-1 flex-col items-center justify-center px-2">
        <div class="w-full rounded-lg bg-white px-6 py-6 text-black shadow-lg">
            <h1 class="mb-6 text-center text-4xl font-semibold">Вхід</h1>

            <form class="mt-4" action="controller" method="post">
                <label>
                    <input type="hidden" class="hidden" name="action" value="login">
                </label>
                <div>
                    <label class="block text-gray-700">Нікнейм</label>
                    <label>
                        <input type="text" name="username" placeholder="Введіть нікнейм"
                               class="mt-2 w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                <c:if test="${username != null}"><c:out value="value=${username}"/></c:if>
                               autocomplete required/>
                    </label>
                    <c:if test="${INVALID_LOGIN != null}">
                        <label class="block text-red-500">${INVALID_LOGIN}</label>
                    </c:if>
                </div>
                <div class="mt-4">
                    <label class="block text-gray-700">Пароль</label>
                    <label>
                        <input type="password" name="password" placeholder="Введіть пароль" minlength="8"
                               class="mt-2 w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                               required/>
                    </label>
                    <c:if test="${INVALID_PASSWORD != null}">
                        <label class="block text-red-500">${INVALID_PASSWORD}</label>
                    </c:if>
                </div>
                <div class="mt-2 text-right">
                    <a href="#"
                       class="text-sm font-semibold text-gray-700 transition-colors duration-300 ease-out hover:text-blue-700 focus:text-blue-700">Забули
                        пароль?</a>
                </div>
                <c:if test="${USER_BLOCKED != null}">
                    <label class="block text-red-500">${USER_BLOCKED}</label>
                </c:if>
                <button type="submit"
                        class="mt-6 block w-full rounded-lg bg-indigo-500 px-4 py-3 font-semibold text-white transition-colors duration-300 ease-out hover:bg-indigo-400 focus:bg-indigo-400">
                    Увійти в обліковий запис
                </button>
            </form>
        </div>

        <div class="mt-6 mb-6 text-gray-700">
            Потрібен новий обліковий запис?
            <a class="border-b border-slate-400 text-sm font-semibold text-gray-700 transition-colors duration-300 ease-out hover:text-blue-700 focus:text-blue-700"
               href="registration"> Реєстрація </a>.
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

</body>
</html>
