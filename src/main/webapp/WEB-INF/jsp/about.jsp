<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/jspf/header.jsp">
    <jsp:param name="pageTitle" value="About Tangail"/>
    <jsp:param name="bodyClass" value="page-about"/>
</jsp:include>

<div class="about-copy">
    <h1>About Tangail District</h1>
    <img class="hero-image"
         src="https://images.unsplash.com/photo-1477587458883-47145ed94245?auto=format&fit=crop&w=1200&q=60"
         alt="River in Bangladesh">
    <p>Tangail is a district of Bangladesh in the Dhaka Division.</p>

    <h2>Geography</h2>
    <p>
        North: Jamalpur<br>
        South: Dhaka and Manikganj<br>
        East: Mymensingh and Gazipur<br>
        West: Sirajganj<br>
        Upazilas: 12 (example: Sakhipur)
    </p>

    <h2>Rivers</h2>
    <p>
        The Jamuna is an important river on the west side.
        The Lohajang river is about 85 km long and goes through / near Tangail town.
    </p>

    <h2>Agriculture</h2>
    <p>Main crops: rice, jute, sugarcane, mustard, wheat and vegetables.</p>

    <h2>Education</h2>
    <p>
        MBSTU is in Santosh, Tangail.
        Other institutions: Mirzapur Cadet College, Tangail Medical College,
        Tangail Textile Engineering College.
    </p>

    <h2>Famous places</h2>
    <p>
        Atiya Mosque, Madhupur National Park, Bangabandhu Bridge,
        Pakutia Zamindar Bari, Mahera Zamindar Bari, Dhanbari Nawab Bari.
        Tangail is also famous for Tangail saree.
    </p>

    <h2>Sources</h2>
    <ul class="source-list">
        <li><a href="https://www.tangail.gov.bd/">https://www.tangail.gov.bd/</a></li>
        <li><a href="https://zp.tangail.gov.bd/">https://zp.tangail.gov.bd/</a></li>
        <li><a href="https://bwdb.tangail.gov.bd/">https://bwdb.tangail.gov.bd/</a></li>
        <li><a href="https://mbstu.ac.bd/">https://mbstu.ac.bd/</a></li>
    </ul>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
