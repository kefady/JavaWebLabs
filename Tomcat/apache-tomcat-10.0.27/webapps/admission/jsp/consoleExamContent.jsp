<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="exams" type="java.util.List"--%>
<%--@elvariable id="INVALID_EXAM_NAME" type="java.lang.String"--%>
<%--@elvariable id="examNames" type="java.util.List"--%>
<html>
<body>
<table class="min-w-max w-full bg-white table-auto mb-4">
    <thead>
    <tr class="bg-gray-200 text-gray-600 uppercase text-sm leading-normal">
        <th class="py-3 px-4 text-center">Id</th>
        <th class="py-3 px-4 text-center">Назва</th>
        <th class="py-3 px-4 text-center break-words">Мінімальний бал</th>
        <th class="py-3 px-4 text-center break-words">Коефіцієнт</th>
        <th class="py-3 px-4 text-center">Додати</th>
    </tr>
    </thead>
    <tbody class="text-gray-600 text-sm font-light">
    <tr class="border-b border-gray-200 hover:bg-gray-100">
        <form action="controller" method="post">
            <label>
                <input class="hidden" hidden="hidden" name="action" value="create_exam">
            </label>
            <td class="py-3 px-6 text-left">NEW</td>
            <td class="py-3 px-6 text-left">
                <label>
                    <select class="mt-2 w-full rounded-lg border bg-gray-200 px-4 py-3 focus:border-blue-500 focus:bg-white focus:outline-none"
                            name="examNameId"
                            required>
                        <option value="" disabled selected>Виберіть назву предмета</option>
                        <c:forEach items="${examNames}" var="examName">
                            <option value="${examName.getId()}">${examName.getName()}</option>
                        </c:forEach>
                    </select>
                </label>
            </td>
            <td class="py-3 px-6 text-center">
                <label>
                    <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                           type="number"
                           name="examMinGrade"
                           min="100"
                           max="160"
                           required>
                </label>
            </td>
            <td class="py-3 px-6 text-center">
                <label>
                    <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                           type="number"
                           name="examCoefficient"
                           min="0.05"
                           max="0.5"
                           step="0.05"
                           required>
                </label>
            </td>
            <td class="py-3 px-6 text-center" style="max-width: 70px" colspan="2">
                <button type="submit">
                    <div class="w-8 mr-2 transform hover:text-green-500 hover:scale-110">
                        <svg xmlns="http://www.w3.org/2000/svg"
                             class="icon icon-tabler icon-tabler-circle-plus" viewBox="0 0 24 24"
                             stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round"
                             stroke-linejoin="round">
                            <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                            <circle cx="12" cy="12" r="9"></circle>
                            <line x1="9" y1="12" x2="15" y2="12"></line>
                            <line x1="12" y1="9" x2="12" y2="15"></line>
                        </svg>
                    </div>
                </button>
            </td>
        </form>
    </tr>
    </tbody>
</table>
</body>
</html>
