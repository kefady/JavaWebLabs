<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="USERNAME_IS_EXIST" type="java.lang.String"--%>
<%--@elvariable id="EMAIL_IS_EXIST" type="java.lang.String"--%>
<%--@elvariable id="INVALID_SURNAME" type="java.lang.String"--%>
<%--@elvariable id="INVALID_NAME" type="java.lang.String"--%>
<%--@elvariable id="INVALID_PATRONYMIC" type="java.lang.String"--%>
<%--@elvariable id="INVALID_CITY" type="java.lang.String"--%>
<%--@elvariable id="INVALID_REGION" type="java.lang.String"--%>
<%--@elvariable id="surname" type="java.lang.String"--%>
<%--@elvariable id="name" type="java.lang.String"--%>
<%--@elvariable id="patronymic" type="java.lang.String"--%>
<%--@elvariable id="username" type="java.lang.String"--%>
<%--@elvariable id="email" type="java.lang.String"--%>
<%--@elvariable id="city" type="java.lang.String"--%>
<%--@elvariable id="region" type="java.lang.String"--%>
<%--@elvariable id="eduName" type="java.lang.String"--%>
<%--@elvariable id="birthday" type="java.sql.Date"--%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>University Admissions | Реєстрація</title>
    <link href="favicon.ico" rel="icon" type="image/x-icon"/>
    <link href="https://unpkg.com/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="flex min-h-screen flex-col bg-gray-100">
    <div class="container mx-auto flex max-w-4xl flex-1 flex-col items-center justify-center px-2">
        <div class="w-full rounded-lg bg-white px-6 py-6 text-black shadow-lg">
            <h1 class="mb-6 text-center text-4xl font-semibold">Реєстрація</h1>

            <form class="mt-4" action="controller" method="post">
                <label>
                    <input type="hidden" class="hidden" name="action" value="registration"/>
                </label>
                <div>
                    <label class="block text-gray-700">Повне ім'я</label>
                    <div class="mt-2 grid grid-cols-1 gap-4 md:grid-cols-3">
                        <label for="surname">
                            <input type="text" name="surname" id="surname" placeholder="Введіть прізвище"
                                   class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                    <c:if test="${surname != null}"><c:out value="value=${surname}"/></c:if>
                                   required/>
                        </label>
                        <label for="name">
                            <input type="text" name="name" id="name" placeholder="Введіть ім'я"
                                   class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                    <c:if test="${name != null}"><c:out value="value=${name}"/></c:if>
                                   required/>
                        </label>
                        <label for="patronymic">
                            <input type="text" name="patronymic" id="patronymic" placeholder="Введіть по-батькові"
                                   class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                    <c:if test="${patronymic != null}"><c:out value="value=${patronymic}"/></c:if>
                                   required/>
                        </label>
                        <c:if test="${INVALID_SURNAME != null}">
                            <label class="block text-red-500">${INVALID_SURNAME}</label>
                        </c:if>
                        <c:if test="${INVALID_NAME != null}">
                            <label class="block text-red-500">${INVALID_NAME}</label>
                        </c:if>
                        <c:if test="${INVALID_PATRONYMIC != null}">
                            <label class="block text-red-500">${INVALID_PATRONYMIC}</label>
                        </c:if>
                    </div>
                </div>

                <div class="mt-4">
                    <label class="block text-gray-700">Придумайте нікнейм та пароль</label>
                    <div class="mt-2 grid grid-cols-1 gap-4 md:grid-cols-3">
                        <label for="username">
                            <input type="text" name="username" id="username" placeholder="Введіть нікнейм"
                                   class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                    <c:if test="${username != null}"><c:out value="value=${username}"/></c:if>
                                   autocomplete required/>
                        </label>
                        <label for="email">
                            <input type="email" name="email" id="email" placeholder="Введіть електронну пошту"
                                   class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                    <c:if test="${email != null}"><c:out value="value=${email}"/></c:if>
                                   autocomplete required/>
                        </label>
                        <label for="password">
                            <input type="password" name="password" id="password" placeholder="Введіть пароль"
                                   class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                   minlength="8" required/>
                        </label>
                        <c:if test="${USERNAME_IS_EXIST != null}">
                            <label class="block text-red-500">${USERNAME_IS_EXIST}</label>
                        </c:if>
                        <c:if test="${EMAIL_IS_EXIST != null}">
                            <label class="block text-red-500">${EMAIL_IS_EXIST}</label>
                        </c:if>
                    </div>
                </div>

                <div class="mt-4">
                    <label class="block text-gray-700">Місце проживання</label>
                    <div class="mt-2 grid grid-cols-1 gap-4 sm:grid-cols-2">
                        <label for="city">
                            <input type="text" name="city" id="city" placeholder="Введіть назву міста/села"
                                   class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                    <c:if test="${city != null}"><c:out value="value=${city}"/></c:if>
                                   required/>
                        </label>
                        <label for="region">
                            <input type="text" name="region" id="region" placeholder="Введіть назву області"
                                   class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                    <c:if test="${region != null}"><c:out value="value=${region}"/></c:if>
                                   required/>
                        </label>
                        <c:if test="${INVALID_CITY != null}">
                            <label class="block text-red-500">${INVALID_CITY}</label>
                        </c:if>
                        <c:if test="${INVALID_REGION != null}">
                            <label class="block text-red-500">${INVALID_REGION}</label>
                        </c:if>
                    </div>
                </div>

                <div class="mt-4">
                    <label class="block text-gray-700">Інше</label>
                    <div class="mt-2 grid grid-cols-1 gap-4 sm:grid-cols-2">
                        <label for="eduName">
                            <input type="text" name="eduName" id="eduName"
                                   placeholder="Введіть назву навчального закладу"
                                   class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                    <c:if test="${eduName != null}"><c:out value="value=${eduName}"/></c:if>
                                   required/>
                        </label>
                        <label for="birthday">
                            <input type="date" name="birthday" id="birthday" placeholder="Введіть дату народження"
                                   class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                    <c:if test="${birthday != null}"><c:out value="value=${birthday}"/></c:if>
                                   required/>
                        </label>
                    </div>
                </div>

                <button type="submit"
                        class="mt-6 block w-full rounded-lg bg-indigo-500 px-4 py-3 font-semibold text-white transition-colors duration-300 ease-out hover:bg-indigo-400 focus:bg-indigo-400">
                    Створити обліковий запис
                </button>
            </form>
        </div>

        <div class="mt-6 mb-6 text-gray-700">
            Уже маєте обліковий запис?
            <a class="border-b border-slate-400 text-sm font-semibold text-gray-700 transition-colors duration-300 ease-out hover:text-blue-700 focus:text-blue-700"
               href="login"> Увійти </a>.
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

</body>
</html>
