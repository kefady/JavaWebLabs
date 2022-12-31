<%--@elvariable id="department" type="com.universityadmissions.entity.Entity"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="CREATE_APPLICATION_ERROR" type="java.lang.String"--%>
<%--@elvariable id="APPLICATION_ERROR" type="java.lang.String"--%>
<%--@elvariable id="APPLICATION_PRIORITY_ERROR" type="java.lang.String"--%>
<%--@elvariable id="APPLICATION_DEPARTMENT_ERROR" type="java.lang.String"--%>
<%--@elvariable id="CERTIFICATE_GRADE_ERROR" type="java.lang.String"--%>
<html>
<head>
    <title>University Admissions | Подача заявки</title>
    <link href="favicon.ico" rel="icon" type="image/x-icon"/>
    <link href="https://unpkg.com/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="flex min-h-screen flex-col bg-gray-100">
    <div class="container mx-auto flex max-w-4xl flex-1 flex-col items-center justify-center px-2 py-8">
        <div class="w-full rounded-lg bg-white px-6 py-6 text-black shadow-lg">
            <h1 class="mb-2 text-center text-4xl font-semibold">Подача вступної заявки на</h1>
            <h2 class="mb-6 text-center text-3xl font-semibold">${department.getName()}</h2>

            <form class="mt-4" action="controller" method="post">
                <label>
                    <input type="hidden" class="hidden" name="action" value="send_application"/>
                    <input type="hidden" class="hidden" name="departmentId" value="${department.getId()}"/>
                </label>
                <div>
                    <table class="min-w-full text-center">
                        <thead class="border-b bg-gray-50">
                        <tr>
                            <th scope="col" class="text-lg font-medium text-gray-900 px-6 py-4">
                                Назва екзамену
                            </th>
                            <th scope="col" class="text-lg font-medium text-gray-900 px-6 py-4">
                                Мінімальний бал
                            </th>
                            <th scope="col" class="text-lg font-medium text-gray-900 px-6 py-4">
                                Коефіцієнт
                            </th>
                            <th scope="col" class="text-lg font-medium text-gray-900 px-6 py-4">
                                Ваш бал
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="bg-white border-b">
                            <td class="px-6 py-4 whitespace-nowrap text-lg font-medium text-gray-900">${department.getFirstExam().getExamName().getName()}</td>
                            <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                                ${department.getFirstExam().getMinGrade()}
                            </td>
                            <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                                ${department.getFirstExam().getCoefficient()}
                            </td>
                            <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                                <input type="hidden" class="hidden" name="firsExamNameId"
                                       value="${department.getFirstExam().getId()}">
                                <label>
                                    <input class="mt-2 w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                           type="number"
                                           name="firstExamGrade"
                                           min="${department.getFirstExam().getMinGrade()}"
                                           max="200"
                                           placeholder="Введіть ваш бал"
                                           required
                                    >
                                </label>
                            </td>
                        </tr>
                        <tr class="bg-white border-b">
                            <td class="px-6 py-4 whitespace-nowrap text-lg font-medium text-gray-900">${department.getSecondExam().getExamName().getName()}</td>
                            <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                                ${department.getSecondExam().getMinGrade()}
                            </td>
                            <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                                ${department.getSecondExam().getCoefficient()}
                            </td>
                            <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                                <input type="hidden" class="hidden" name="secondExamNameId"
                                       value="${department.getSecondExam().getId()}">
                                <label>
                                    <input class="mt-2 w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                           type="number"
                                           name="secondExamGrade"
                                           min="${department.getSecondExam().getMinGrade()}"
                                           max="200"
                                           placeholder="Введіть ваш бал"
                                           required
                                    >
                                </label>
                            </td>
                        </tr>
                        <tr class="bg-white border-b">
                            <td class="px-6 py-4 whitespace-nowrap text-lg font-medium text-gray-900">${department.getThirdExam().getExamName().getName()}</td>
                            <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                                ${department.getThirdExam().getMinGrade()}
                            </td>
                            <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                                ${department.getThirdExam().getCoefficient()}
                            </td>
                            <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                                <input type="hidden" class="hidden" name="thirdExamNameId"
                                       value="${department.getThirdExam().getId()}">
                                <label>
                                    <input class="mt-2 w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                           type="number"
                                           name="thirdExamGrade"
                                           min="${department.getThirdExam().getMinGrade()}"
                                           max="200"
                                           placeholder="Введіть ваш бал"
                                           required
                                    >
                                </label>
                            </td>
                        </tr>
                        <tr class="bg-white border-b">
                            <td class="text-2xl text-gray-900 font-light px-6 py-4 whitespace-nowrap" colspan="4">
                                Оцінки зі сертифікату
                            </td>
                        </tr>
                        <c:forEach begin="1" end="3" step="1" var="i">
                            <tr class="bg-white border-b">
                                <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap" colspan="2">
                                    <label>
                                        <select class="mt-2 w-full rounded-lg border bg-gray-200 px-4 py-3 focus:border-blue-500 focus:bg-white focus:outline-none"
                                                id="certificate"
                                                name="certificateGradeExamNameId${i}"
                                                required>
                                            <option value="" disabled selected>Виберіть назву предмета</option>
                                                <%--@elvariable id="examNames" type="java.util.List"--%>
                                            <c:forEach items="${examNames}" var="examName">
                                                <option value="${examName.getId()}">${examName.getName()}</option>
                                            </c:forEach>
                                        </select>
                                    </label>
                                </td>
                                <td class="text-lg text-gray-900 font-light px-6 py-4 whitespace-nowrap" colspan="2">
                                    <label>
                                        <input class="mt-2 w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                               type="number"
                                               name="certificateGrade${i}"
                                               min="0"
                                               max="12"
                                               placeholder="Введіть ваш бал"
                                               required
                                        >
                                    </label>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="mt-6 flex gap-4 px-4">
                    <label>
                        <input class="w-full rounded-lg border bg-gray-200 px-4 py-3 transition-colors duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                               type="number" name="priority" min="1" max="5" placeholder="Введіть пріорітет заявки"
                               required/>
                    </label>
                    <button type="submit"
                            class="block w-full rounded-lg bg-indigo-500 px-4 py-3 font-semibold text-white transition-colors duration-300 ease-out hover:bg-indigo-400 focus:bg-indigo-400">
                        Подати заявку
                    </button>
                </div>
            </form>
            <div class="w-full">
                <c:if test="${APPLICATION_PRIORITY_ERROR != null}">
                    <p class="block mb-1 text-red-500">${APPLICATION_PRIORITY_ERROR}</p>
                </c:if>
                <c:if test="${APPLICATION_DEPARTMENT_ERROR != null}">
                    <p class="block mb-1 text-red-500">${APPLICATION_DEPARTMENT_ERROR}</p>
                </c:if>
                <c:if test="${CERTIFICATE_GRADE_ERROR != null}">
                    <p class="block mb-1 text-red-500">${CERTIFICATE_GRADE_ERROR}</p>
                </c:if>
                <c:if test="${APPLICATION_ERROR != null}">
                    <p class="block mb-1 text-red-500">${APPLICATION_ERROR}</p>
                </c:if>
                <c:if test="${CREATE_APPLICATION_ERROR != null}">
                    <p class="block mb-1 text-red-500">${CREATE_APPLICATION_ERROR}</p>
                </c:if>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

</body>
</html>
