<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="departments" type="java.util.List"--%>
<%--@elvariable id="listOfApplyDepartment" type="java.util.List<Integer>"--%>
<%--@elvariable id="countOfUserApplications" type="java.lang.Integer"--%>

<!DOCTYPE html>
<html lang="en" class="antialiased">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>University Admissions</title>
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

<%@ include file="jsp/header.jsp" %>

<div class="container w-full md:w-4/5 xl:w-full  mx-auto px-2 my-4">
    <div id='recipients' class="p-8 mt-6 lg:mt-0 rounded shadow bg-white">
        <table id="example" class="stripe hover" style="width:100%; padding-top: 1em;  padding-bottom: 1em;">
            <thead>
            <tr>
                <th data-priority="1">Id</th>
                <th data-priority="2">Назва</th>
                <th data-priority="3">Бюджетні місця</th>
                <th data-priority="4">Кількість місць</th>
                <th data-priority="5">Перший екзамен</th>
                <th data-priority="6">Другий екзамен</th>
                <th data-priority="7">Третій екзамен</th>
                <th>Подати заявку</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${departments}" var="department">
                <tr>
                    <td>${department.getId()}</td>
                    <td>${department.getName()}</td>
                    <td>${department.getBudgetPlace()}</td>
                    <td>${department.getAllPlace()}</td>
                    <td>${department.getFirstExam().getExamName().getName()}</td>
                    <td>${department.getSecondExam().getExamName().getName()}</td>
                    <td>${department.getThirdExam().getExamName().getName()}</td>
                    <td>
                        <form action="controller" method="get">
                            <label>
                                <input type="hidden" class="hidden" name="action" value="apply">
                            </label>
                            <label>
                                <input type="hidden" class="hidden" name="departmentId" value="${department.getId()}">
                            </label>
                            <button type="submit"
                                    class="inline-block px-6 py-2.5 bg-blue-800 text-white font-medium text-xs leading-tight uppercase rounded shadow-lg hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-300 ease-in-out"
                                    <c:forEach items="${listOfApplyDepartment}" var="applyDepartment">
                                        <c:if test="${applyDepartment == department.getId() || countOfUserApplications >= 5}"><c:out
                                                value="disabled='disabled' style=background-color:silver"/></c:if>
                                    </c:forEach>
                            >
                                Подати
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="jsp/footer.jsp" %>

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


