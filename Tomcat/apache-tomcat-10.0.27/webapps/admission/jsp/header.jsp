<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="isLogin" type="java.lang.Boolean"--%>
<%--@elvariable id="role" type="java.lang.String"--%>
<html>
<body>
<header class="relative bg-white">
    <div class="mx-auto px-6">
        <div class="flex items-center justify-between border-b-2 border-gray-100 py-6 md:justify-start md:space-x-10">
            <a href="${pageContext.request.contextPath}"
               class="mr-8 w-auto text-xl font-bold text-gray-800 sm:text-2xl md:text-3xl hover:text-blue-500">
                University
                Admissions </a>
            <div class="items-center justify-end md:flex md:flex-1 lg:w-0">
                <c:if test="${isLogin != true}">
                    <a href="login"
                       class="whitespace-nowrap text-base font-medium text-gray-500 transition-colors duration-200 ease-in-out hover:text-gray-900">
                        Вхід
                    </a>
                    <a href="registration"
                       class="ml-8 inline-flex items-center justify-center whitespace-nowrap rounded-md border border-transparent bg-blue-500 px-4 py-2 text-base font-medium text-white shadow-sm transition-colors duration-200 ease-in-out hover:bg-blue-700">
                        Реєстрація
                    </a>
                </c:if>
                <c:if test="${isLogin == true}">
                    <c:if test="${role == 'ADMIN'}">
                        <form action="controller" method="get">
                            <label>
                                <input hidden="hidden" class="hidden" name="action" value="console">
                            </label>
                            <button type="submit"
                                    class="inline-block px-6 py-2.5 bg-white text-gray-500 font-medium text-xs leading-tight uppercase rounded hover:text-gray-700 focus:outline-none focus:ring-0 transition duration-300 ease-in-out"
                            >
                                Консоль
                            </button>
                        </form>
                    </c:if>
                    <form action="controller" method="get">
                        <label>
                            <input hidden="hidden" class="hidden" name="action" value="profile">
                        </label>
                        <button type="submit"
                                class="inline-block px-6 py-2.5 bg-blue-500 text-white font-medium text-xs leading-tight uppercase rounded hover:bg-blue-700 focus:outline-none focus:ring-0 transition duration-300 ease-in-out"
                        >
                            Профіль
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</header>
</body>
</html>
