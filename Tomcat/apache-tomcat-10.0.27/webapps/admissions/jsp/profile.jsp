<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="applications" type="java.util.List"--%>
<%--@elvariable id="surname" type="java.lang.String"--%>
<%--@elvariable id="name" type="java.lang.String"--%>
<%--@elvariable id="patronymic" type="java.lang.String"--%>
<%--@elvariable id="username" type="java.lang.String"--%>
<%--@elvariable id="email" type="java.lang.String"--%>
<%--@elvariable id="role" type="java.lang.String"--%>
<%--@elvariable id="eduName" type="java.lang.String"--%>
<%--@elvariable id="birthday" type="java.sql.Date"--%>
<%--@elvariable id="isApplyEnd" type="java.lang.Boolean"--%>

<!DOCTYPE html>
<html lang="en" class="antialiased">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>University Admissions | Профіль</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link href="favicon.ico" rel="icon" type="image/x-icon"/>
    <link href="https://unpkg.com/tailwindcss@2.2.19/dist/tailwind.min.css" rel=" stylesheet">
    <link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css" rel="stylesheet">

    <style>
        .dataTables_wrapper select,
        .dataTables_wrapper .dataTables_filter input {
            color: #4a5568;
            padding: .5rem 1rem;
            line-height: 1.25;
            border-width: 2px;
            border-radius: .25rem;
            border-color: #edf2f7;
            background-color: #edf2f7;
        }

        table.dataTable.hover tbody tr:hover,
        table.dataTable.display tbody tr:hover {
            background-color: #ebf4ff;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button {
            font-weight: 700;
            border-radius: .25rem;
            border: 1px solid transparent;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button.current {
            color: #fff !important;
            box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .1), 0 1px 2px 0 rgba(0, 0, 0, .06);
            font-weight: 700;
            border-radius: .25rem;
            background: #667eea !important;
            border: 1px solid transparent;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            color: #fff !important;
            box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .1), 0 1px 2px 0 rgba(0, 0, 0, .06);
            font-weight: 700;
            border-radius: .25rem;
            background: #667eea !important;
            border: 1px solid transparent;
        }

        table.dataTable.no-footer {
            border-bottom: 1px solid #e2e8f0;
            margin-top: 0.75em;
            margin-bottom: 0.75em;
        }

        table.dataTable.dtr-inline.collapsed > tbody > tr > td:first-child:before,
        table.dataTable.dtr-inline.collapsed > tbody > tr > th:first-child:before {
            background-color: #667eea !important;
        }
    </style>


</head>
<body class="bg-gray-100 text-gray-900 tracking-wider leading-normal">

<%@ include file="header.jsp" %>

<div class="container w-full md:w-4/5 xl:w-full  mx-auto px-2 my-4">
    <div id='recipients' class="p-8 mt-6 lg:mt-0 rounded shadow bg-white">
        <div class="w-full mb-8">
            <div class="h-48 flex-none overflow-hidden rounded-t bg-cover text-center"
                 style="background-image: url('https://source.unsplash.com/random/1920x1080?sig=1')"></div>
            <div class="flex flex-col justify-between rounded-b border-r border-b border-l border-gray-400 bg-white p-4 leading-normal">
                <div class="mb-4">
                    <p class="flex items-center text-sm text-gray-600">
                        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-user-circle mr-1"
                             width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor"
                             fill="none" stroke-linecap="round" stroke-linejoin="round">
                            <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                            <circle cx="12" cy="12" r="9"></circle>
                            <circle cx="12" cy="10" r="3"></circle>
                            <path d="M6.168 18.849a4 4 0 0 1 3.832 -2.849h4a4 4 0 0 1 3.834 2.855"></path>
                        </svg>
                        ${role}
                    </p>
                    <div class="mb-2 text-xl font-bold text-gray-900">${surname} ${name} ${patronymic}</div>
                    <p class="text-base text-gray-700"><span class="font-bold">Нікнейм:</span> ${username}</p>
                    <p class="text-base text-gray-700"><span class="font-bold">Електронна пошта:</span> ${email}</p>
                    <p class="text-base text-gray-700"><span class="font-bold">Дата народження:</span> ${birthday}</p>
                    <p class="text-base text-gray-700"><span class="font-bold">Закінчив:</span> ${eduName}</p>
                    <form action="controller" method="post" class="mt-4">
                        <label>
                            <input class="hidden" hidden="hidden" name="action" value="logout">
                        </label>
                        <button type="submit"
                                class="block max-w-sm rounded-lg bg-indigo-500 px-4 py-3 font-semibold text-white transition-colors duration-300 ease-out hover:bg-indigo-400 focus:bg-indigo-400">
                            Вийти з облікового запису
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <p class="text-3xl text-center font-bold">Ваші заявки</p>

        <table id="example" class="stripe hover" style="width:100%; padding-top: 1em;  padding-bottom: 1em; text-align: center;">
            <thead>
            <tr>
                <th data-priority="1">Id</th>
                <th data-priority="2">Факультет</th>
                <th data-priority="3">Пріорітет</th>
                <th data-priority="4">Фінальний бал</th>
                <th data-priority="5">Верифікація</th>
                <th data-priority="6">Статус</th>
                <th data-priority="7">Видалити</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${applications}" var="application">
                <tr>
                    <td>${application.getId()}</td>
                    <td>${application.getDepartment().getName()}</td>
                    <td>${application.getPriority()}</td>
                    <td>${application.getFinalGrade()}</td>
                    <td>
                        <c:if test="${application.isVerified() == false}">
                            <span class="text-red-500">Не верифікована</span>
                        </c:if>
                        <c:if test="${application.isVerified() == true}">
                            <span class="text-green-500">Верифікована</span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${application.isAccepted() == false}">
                            <c:if test="${isApplyEnd != true}">
                                <span class="text-yellow-500">На розгляді</span>
                            </c:if>
                            <c:if test="${isApplyEnd == true}">
                                <span class="text-red-500">Не пройшла</span>
                            </c:if>
                        </c:if>
                        <c:if test="${application.isAccepted() == true}">
                            <span class="text-green-500">Пройшла</span>
                        </c:if>
                    </td>
                    <td>
                        <form action="controller" method="post">
                            <label>
                                <input type="hidden" class="hidden" name="action" value="delete_user_application">
                                <input type="hidden" class="hidden" name="applicationId" value="${application.getId()}">
                                <input type="hidden" class="hidden" name="departmentId" value="${application.getDepartment().getId()}">
                            </label>
                            <button type="submit">
                                <div class="w-8 mr-2 transform hover:text-red-500 hover:scale-110">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none"
                                         viewBox="0 0 24 24"
                                         stroke="currentColor">
                                        <path stroke-linecap="round" stroke-linejoin="round"
                                              stroke-width="2"
                                              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                                    </svg>
                                </div>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
<script>
    $(document).ready(function () {

        let table = $('#example').DataTable({
            responsive: true
        })
            .columns.adjust()
            .responsive.recalc();
    });
</script>
</body>
</html>


