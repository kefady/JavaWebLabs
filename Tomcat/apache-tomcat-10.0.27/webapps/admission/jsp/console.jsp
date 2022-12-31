<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="departments" type="java.util.List"--%>
<%--@elvariable id="exams" type="java.util.List"--%>
<%--@elvariable id="users" type="java.util.List"--%>
<%--@elvariable id="examNames" type="java.util.List"--%>
<%--@elvariable id="applications" type="java.util.List"--%>
<%--@elvariable id="chosenTap" type="java.lang.Integer"--%>
<%--@elvariable id="INVALID_EXAM_NAME" type="java.lang.String"--%>
<%--@elvariable id="FIRST_EXAM_ERROR" type="java.lang.String"--%>
<%--@elvariable id="SECOND_EXAM_ERROR" type="java.lang.String"--%>
<%--@elvariable id="THIRD_EXAM_ERROR" type="java.lang.String"--%>
<%--@elvariable id="INVALID_DEPARTMENT_NAME" type="java.lang.String"--%>
<%--@elvariable id="BUDGET_PlACE_ERROR" type="java.lang.String"--%>
<%--@elvariable id="EXAM_ERROR" type="java.lang.String"--%>
<%--@elvariable id="COEFFICIENT_ERROR" type="java.lang.String"--%>
<%--@elvariable id="USER_BLOCK_ROLE_ERROR" type="java.lang.String"--%>
<%--@elvariable id="CREATE_EXAM_ERROR" type="java.lang.String"--%>
<%--@elvariable id="APPLICATION_VERIFY_ERROR" type="java.lang.String"--%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>University Admissions | Console</title>
    <link href="favicon.ico" rel="icon" type="image/x-icon"/>
    <link href="https://unpkg.com/tailwindcss@2.2.19/dist/tailwind.min.css" rel=" stylesheet">
    <link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css" rel="stylesheet">
    <link href="https://unpkg.com/@themesberg/flowbite@1.2.0/dist/flowbite.min.css" rel="stylesheet"/>

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

    <div class="border-b border-gray-200 dark:border-gray-700 mb-4">
        <ul class="flex flex-wrap -mb-px" id="console" data-tabs-toggle="#consoleContent" role="tablist">
            <li class="mr-2" role="presentation">
                <button class="inline-block text-gray-500 hover:text-gray-600 hover:border-gray-300 rounded-t-lg py-4 px-4 text-lg font-medium text-center border-transparent border-b-2 dark:text-gray-400 dark:hover:text-gray-300 active"
                        id="department-tab" data-tabs-target="#department" type="button" role="tab"
                        aria-controls="department"
                        aria-selected="true">Факультети
                </button>
            </li>
            <li class="mr-2" role="presentation">
                <button class="inline-block text-gray-500 hover:text-gray-600 hover:border-gray-300 rounded-t-lg py-4 px-4 text-lg font-medium text-center border-transparent border-b-2 dark:text-gray-400 dark:hover:text-gray-300"
                        id="exam-tab" data-tabs-target="#exam" type="button" role="tab"
                        aria-controls="exam"
                        aria-selected="false">Екзамени
                </button>
            </li>
            <li class="mr-2" role="presentation">
                <button class="inline-block text-gray-500 hover:text-gray-600 hover:border-gray-300 rounded-t-lg py-4 px-4 text-lg font-medium text-center border-transparent border-b-2 dark:text-gray-400 dark:hover:text-gray-300"
                        id="user-tab" data-tabs-target="#user" type="button" role="tab" aria-controls="user"
                        aria-selected="false">Користувачі
                </button>
            </li>
            <li role="presentation">
                <button class="inline-block text-gray-500 hover:text-gray-600 hover:border-gray-300 rounded-t-lg py-4 px-4 text-lg font-medium text-center border-transparent border-b-2 dark:text-gray-400 dark:hover:text-gray-300"
                        id="application-tab" data-tabs-target="#application" type="button" role="tab"
                        aria-controls="application"
                        aria-selected="false">Заявки
                </button>
            </li>
        </ul>
    </div>
    <div id="consoleContent" class="overflow-x-auto">

        <%@ include file="consoleDepartmentContent.jsp" %>

        <div class="bg-gray-50 p-4 rounded-lg dark:bg-gray-800 hidden" id="exam" role="tabpanel"
             aria-labelledby="exam-tab">
            <div class="mb-4 bg-gray-100">
                <c:if test="${CREATE_EXAM_ERROR != null}">
                    <p class="block mb-1 text-red-500">${CREATE_EXAM_ERROR}</p>
                </c:if>
            </div>

            <%@ include file="consoleExamContent.jsp" %>

            <div class="p-8 mt-6 lg:mt-0 rounded shadow bg-white">
                <table id="exams" class="stripe hover"
                       style="width:100%; padding-top: 1em;  padding-bottom: 1em; text-align: center;">
                    <thead>
                    <tr>
                        <th data-priority="1">Id</th>
                        <th data-priority="2">Назва</th>
                        <th data-priority="3">Мінімальний бал</th>
                        <th data-priority="4">Коефіцієнт</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${exams}" var="exam">
                        <tr>
                            <td>${exam.getId()}</td>
                            <td>${exam.getExamName().getName()}</td>
                            <td>${exam.getMinGrade()}</td>
                            <td>${exam.getCoefficient()}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="bg-gray-50 p-4 rounded-lg dark:bg-gray-800 hidden" id="user" role="tabpanel"
             aria-labelledby="user-tab">
            <div class="mb-4 bg-gray-100">
                <c:if test="${USER_BLOCK_ROLE_ERROR != null}">
                    <p class="block mb-1 text-red-500">${USER_BLOCK_ROLE_ERROR}</p>
                </c:if>
            </div>
            <div class="p-8 mt-6 lg:mt-0 rounded shadow bg-white">
                <table id="users" class="stripe hover"
                       style="width:100%; padding-top: 1em;  padding-bottom: 1em; text-align: center;">
                    <thead>
                    <tr>
                        <th data-priority="1">Id</th>
                        <th data-priority="2">Прізвище</th>
                        <th data-priority="3">Ім'я</th>
                        <th data-priority="4">Нікнейм</th>
                        <th data-priority="5">Заблокований</th>
                        <th>Блокування</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.getId()}</td>
                            <td>${user.getSurname()}</td>
                            <td>${user.getName()}</td>
                            <td>${user.getUsername()}</td>
                            <td>${user.isBlocked()}</td>
                            <td>
                                <form action="controller" method="post">
                                    <input hidden="hidden" class="hidden" name="userId" value="${user.getId()}">
                                    <c:choose>
                                        <c:when test="${user.isBlocked() != true}">
                                            <input hidden="hidden" class="hidden" name="action" value="block_user">
                                            <button type="submit">
                                                <div class="w-8 mr-2 transform hover:text-red-500 hover:scale-110">
                                                    <svg xmlns="http://www.w3.org/2000/svg"
                                                         class="icon icon-tabler icon-tabler-user-off"
                                                         viewBox="0 0 24 24"
                                                         stroke-width="2" stroke="currentColor" fill="none"
                                                         stroke-linecap="round" stroke-linejoin="round">
                                                        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                                                        <path d="M14.274 10.291a4 4 0 1 0 -5.554 -5.58m-.548 3.453a4.01 4.01 0 0 0 2.62 2.65"></path>
                                                        <path d="M6 21v-2a4 4 0 0 1 4 -4h4a4 4 0 0 1 1.147 .167m2.685 2.681a4 4 0 0 1 .168 1.152v2"></path>
                                                        <line x1="3" y1="3" x2="21" y2="21"></line>
                                                    </svg>
                                                </div>
                                            </button>
                                        </c:when>
                                        <c:when test="${user.isBlocked() == true}">
                                            <input hidden="hidden" class="hidden" name="action"
                                                   value="unblock_user">
                                            <button type="submit">
                                                <div class="w-8 mr-2 transform hover:text-green-500 hover:scale-110">
                                                    <svg xmlns="http://www.w3.org/2000/svg"
                                                         class="icon icon-tabler icon-tabler-user-check"
                                                         viewBox="0 0 24 24" stroke-width="2" stroke="currentColor"
                                                         fill="none" stroke-linecap="round" stroke-linejoin="round">
                                                        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                                                        <circle cx="9" cy="7" r="4"></circle>
                                                        <path d="M3 21v-2a4 4 0 0 1 4 -4h4a4 4 0 0 1 4 4v2"></path>
                                                        <path d="M16 11l2 2l4 -4"></path>
                                                    </svg>
                                                </div>
                                            </button>
                                        </c:when>
                                    </c:choose>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="bg-gray-50 p-4 rounded-lg dark:bg-gray-800 hidden" id="application" role="tabpanel"
             aria-labelledby="application-tab">
            <div class="mb-4 flex justify-between">
                <c:if test="${APPLICATION_VERIFY_ERROR != null}">
                    <p class="block mb-1 text-red-500">${APPLICATION_VERIFY_ERROR}</p>
                </c:if>
                <form action="controller" method="post" class="max-w-lg">
                    <label>
                        <input hidden="hidden" class="hidden" name="action" value="start_election">
                    </label>
                    <button type="submit"
                            class="inline-block px-6 py-2.5 bg-blue-500 text-white font-medium text-xs leading-tight uppercase rounded hover:bg-blue-700 focus:outline-none focus:ring-0 transition duration-300 ease-in-out">
                        Почати відбір
                    </button>
                </form>
            </div>
            <div class="p-8 mt-6 lg:mt-0 rounded shadow bg-white">
                <table id="applications" class="stripe hover"
                       style="width:100%; padding-top: 1em;  padding-bottom: 1em; text-align: center;">
                    <thead>
                    <tr>
                        <th data-priority="1">Id</th>
                        <th data-priority="2">Username</th>
                        <th data-priority="3">Факультет</th>
                        <th data-priority="4">Результат</th>
                        <th data-priority="5">Верифікована</th>
                        <th>Верифікація</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${applications}" var="application">
                        <tr>
                            <td>${application.getId()}</td>
                            <td>${application.getUser().getUsername()}</td>
                            <td>${application.getDepartment().getName()}</td>
                            <td>${application.getFinalGrade()}</td>
                            <td>${application.isVerified()}</td>
                            <td>
                                <form action="controller" method="post">
                                    <label>
                                        <input hidden="hidden" class="hidden" name="action"
                                               value="verified_application">
                                        <input hidden="hidden" class="hidden" name="applicationId"
                                               value="${application.getId()}">
                                    </label>
                                    <c:choose>
                                        <c:when test="${application.isVerified() != true}">
                                            <button type="submit">
                                                <div class="w-8 mr-2 transform hover:text-green-500 hover:scale-110">
                                                    <svg xmlns="http://www.w3.org/2000/svg"
                                                         class="icon icon-tabler icon-tabler-circle-check"
                                                         viewBox="0 0 24 24" stroke-width="2" stroke="currentColor"
                                                         fill="none" stroke-linecap="round" stroke-linejoin="round">
                                                        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                                                        <circle cx="12" cy="12" r="9"></circle>
                                                        <path d="M9 12l2 2l4 -4"></path>
                                                    </svg>
                                                </div>
                                            </button>
                                        </c:when>
                                        <c:when test="${application.isVerified() == true}">
                                            <button>
                                                <div class="w-8 mr-2 text-gray-500">
                                                    <svg xmlns="http://www.w3.org/2000/svg"
                                                         class="icon icon-tabler icon-tabler-checks" viewBox="0 0 24 24"
                                                         stroke-width="2" stroke="currentColor" fill="none"
                                                         stroke-linecap="round" stroke-linejoin="round">
                                                        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                                                        <path d="M7 12l5 5l10 -10"></path>
                                                        <path d="M2 12l5 5m5 -5l5 -5"></path>
                                                    </svg>
                                                </div>
                                            </button>
                                        </c:when>
                                    </c:choose>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
<script src="https://unpkg.com/@themesberg/flowbite@1.2.0/dist/flowbite.bundle.js"></script>
<script>
    $(document).ready(function () {

        let table = $('#exams').DataTable({
            responsive: true
        })
            .columns.adjust()
            .responsive.recalc();
        let table2 = $('#users').DataTable({
            responsive: true
        })
            .columns.adjust()
            .responsive.recalc();
        let table3 = $('#applications').DataTable({
            responsive: true
        })
            .columns.adjust()
            .responsive.recalc();
    });
</script>
</body>
</html>

