<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="departments" type="java.util.List"--%>
<%--@elvariable id="exams" type="java.util.List"--%>
<%--@elvariable id="FIRST_EXAM_ERROR" type="java.lang.String"--%>
<%--@elvariable id="SECOND_EXAM_ERROR" type="java.lang.String"--%>
<%--@elvariable id="THIRD_EXAM_ERROR" type="java.lang.String"--%>
<%--@elvariable id="INVALID_DEPARTMENT_NAME" type="java.lang.String"--%>
<%--@elvariable id="BUDGET_PlACE_ERROR" type="java.lang.String"--%>
<%--@elvariable id="EXAM_ERROR" type="java.lang.String"--%>
<%--@elvariable id="COEFFICIENT_ERROR" type="java.lang.String"--%>
<html>
<body>
<div class="w-full shadow-md rounded my-6"
     id="department" role="tabpanel"
     aria-labelledby="department-tab">
    <div class="mb-4 bg-gray-100">
        <c:if test="${INVALID_DEPARTMENT_NAME != null}">
            <p class="block mb-1 text-red-500">${INVALID_DEPARTMENT_NAME}</p>
        </c:if>
        <c:if test="${FIRST_EXAM_ERROR != null}">
            <p class="block mb-1 text-red-500">${FIRST_EXAM_ERROR}</p>
        </c:if>
        <c:if test="${SECOND_EXAM_ERROR != null}">
            <p class="block mb-1 text-red-500">${SECOND_EXAM_ERROR}</p>
        </c:if>
        <c:if test="${THIRD_EXAM_ERROR != null}">
            <p class="block mb-1 text-red-500">${THIRD_EXAM_ERROR}</p>
        </c:if>
        <c:if test="${BUDGET_PlACE_ERROR != null}">
            <p class="block mb-1 text-red-500">${BUDGET_PlACE_ERROR}</p>
        </c:if>
        <c:if test="${COEFFICIENT_ERROR != null}">
            <p class="block mb-1 text-red-500">${COEFFICIENT_ERROR}</p>
        </c:if>
        <c:if test="${EXAM_ERROR != null}">
            <p class="block mb-1 text-red-500">${EXAM_ERROR}</p>
        </c:if>
    </div>
    <table class="min-w-max w-full bg-white table-auto">
        <thead>
        <tr class="bg-gray-200 text-gray-600 uppercase text-sm leading-normal">
            <th class="py-3 px-4 text-center">Id</th>
            <th class="py-3 px-4 text-center">Назва</th>
            <th class="py-3 px-4 text-center break-words" style="max-width: 150px">Бюджетні місця</th>
            <th class="py-3 px-4 text-center break-words" style="max-width: 150px">Кількість місць</th>
            <th class="py-3 px-4 text-center break-words" style="max-width: 150px">Екзамен №1 (ID)</th>
            <th class="py-3 px-4 text-center break-words" style="max-width: 150px">Екзамен №2 (ID)</th>
            <th class="py-3 px-4 text-center break-words" style="max-width: 150px">Екзамен №3 (ID)</th>
            <th class="py-3 px-4 text-center" colspan="2" style="max-width: 150px">Дії</th>
        </tr>
        </thead>
        <tbody class="text-gray-600 text-sm font-light">
        <tr class="border-b border-gray-200 hover:bg-gray-100">
            <form action="controller" method="post">
                <label>
                    <input class="hidden" hidden="hidden" name="action" value="create_department">
                </label>
                <td class="py-3 px-6 text-left">NEW</td>
                <td class="py-3 px-6 text-left">
                    <label>
                        <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                               type="text"
                               name="departmentName"
                               minlength="10"
                               required>
                    </label>
                </td>
                <td class="py-3 px-6 text-center" style="max-width: 150px">
                    <label>
                        <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                               type="number"
                               name="departmentBudgetPlace"
                               min="0"
                               max="500"
                               required>
                    </label>
                </td>
                <td class="py-3 px-6 text-center" style="max-width: 150px">
                    <label>
                        <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                               type="number"
                               name="departmentAllPlace"
                               min="0"
                               max="1000"
                               required>
                    </label>
                </td>
                <td class="py-3 px-6 text-center" style="max-width: 100px">
                    <label>
                        <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                               type="number"
                               name="departmentFirstExamId"
                               min="0"
                               max="${exams.size()}}"
                               required>
                    </label>
                </td>
                <td class="py-3 px-6 text-center" style="max-width: 100px">
                    <label>
                        <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                               type="number"
                               name="departmentSecondExamId"
                               min="0"
                               max="${exams.size()}}"
                               required>
                    </label>
                </td>
                <td class="py-3 px-6 text-center" style="max-width: 100px">
                    <label>
                        <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                               type="number"
                               name="departmentThirdExamId"
                               min="0"
                               max="${exams.size()}}"
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
        <c:forEach items="${departments}" var="department">
            <tr class="border-b border-gray-200 hover:bg-gray-100">
                <form action="controller" method="post">
                    <label>
                        <input class="hidden" hidden="hidden" name="action" value="update_department">
                        <input class="hidden" hidden="hidden" name="departmentId" value="${department.getId()}">
                    </label>
                    <td class="py-3 px-6 text-left">${department.getId()}</td>
                    <td class="py-3 px-6 text-left">
                        <label>
                            <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                   type="text"
                                   name="departmentName"
                                   value="${department.getName()}"
                                   minlength="10"
                                   required>
                        </label>
                    </td>
                    <td class="py-3 px-6 text-center" style="max-width: 150px">
                        <label>
                            <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                   type="number"
                                   name="departmentBudgetPlace"
                                   value="${department.getBudgetPlace()}"
                                   min="0"
                                   max="500"
                                   required>
                        </label>
                    </td>
                    <td class="py-3 px-6 text-center" style="max-width: 150px">
                        <label>
                            <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                   type="number"
                                   name="departmentAllPlace"
                                   value="${department.getAllPlace()}"
                                   min="0"
                                   max="1000"
                                   required>
                        </label>
                    </td>
                    <td class="py-3 px-6 text-center" style="max-width: 100px">
                        <label>
                            <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                   type="number"
                                   name="departmentFirstExamId"
                                   value="${department.getFirstExam().getId()}"
                                   min="0"
                                   max="${exams.size()}}"
                                   required>
                        </label>
                    </td>
                    <td class="py-3 px-6 text-center" style="max-width: 100px">
                        <label>
                            <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                   type="number"
                                   name="departmentSecondExamId"
                                   value="${department.getSecondExam().getId()}"
                                   min="0"
                                   max="${exams.size()}}"
                                   required>
                        </label>
                    </td>
                    <td class="py-3 px-6 text-center" style="max-width: 100px">
                        <label>
                            <input class="w-full rounded-lg border bg-gray-200 transition-colors
                                        duration-300 ease-out focus:border-blue-500 focus:bg-white focus:outline-none"
                                   type="number"
                                   name="departmentThirdExamId"
                                   value="${department.getThirdExam().getId()}"
                                   min="0"
                                   max="${exams.size()}}"
                                   required>
                        </label>
                    </td>
                    <td class="py-3 px-6 text-center" style="max-width: 70px">
                        <button type="submit">
                            <div class="w-8 mr-2 transform hover:text-blue-500 hover:scale-110">
                                <svg xmlns="http://www.w3.org/2000/svg"
                                     class="icon icon-tabler icon-tabler-device-floppy"
                                     viewBox="0 0 24 24"
                                     stroke-width="2" stroke="currentColor" fill="none"
                                     stroke-linecap="round"
                                     stroke-linejoin="round">
                                    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                                    <path d="M6 4h10l4 4v10a2 2 0 0 1 -2 2h-12a2 2 0 0 1 -2 -2v-12a2 2 0 0 1 2 -2"></path>
                                    <circle cx="12" cy="14" r="2"></circle>
                                    <polyline points="14 4 14 8 8 8 8 4"></polyline>
                                </svg>
                            </div>
                        </button>
                    </td>
                </form>
                <form action="controller" method="post">
                    <label>
                        <input class="hidden" hidden="hidden" name="action" value="delete_department">
                        <input class="hidden" hidden="hidden" name="departmentId" value="${department.getId()}">
                    </label>
                    <td class="py-3 px-6 text-center" style="max-width: 70px">
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
                    </td>
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
