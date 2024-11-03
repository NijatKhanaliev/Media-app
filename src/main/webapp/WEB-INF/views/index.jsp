<%@ page import="com.company.media.model.MediaPost" %>
<%@ page import="java.util.List" %>
<%@ page import="com.company.media.dto.MediaPostWithFollowAndLikeStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="<%=request.getContextPath()%>/assets/js/follow.js" defer></script>
    <script src="<%=request.getContextPath()%>/assets/js/like.js" defer></script>

    <%
       @SuppressWarnings("unchecked")
        List<MediaPostWithFollowAndLikeStatus> mediaPostWithFollowAndLikeStatusList = (List<MediaPostWithFollowAndLikeStatus>) request.getAttribute("mediaPostWithFollowAndLikeStatusList");
    %>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Media App</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/create">Create Post</a>
                </li>
            </ul>
            <form class="d-flex" role="search" action="/search" method="POST">
                <input class="form-control me-2" type="search" name="name" placeholder="Search By name" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>
<%
    if(mediaPostWithFollowAndLikeStatusList == null || mediaPostWithFollowAndLikeStatusList.isEmpty()){
   %>
<h1>No media post available.</h1>
<%
    }else{
        %>
<div class="row">
<%
        for(MediaPostWithFollowAndLikeStatus m : mediaPostWithFollowAndLikeStatusList){
            MediaPost mediaPost = m.getMediaPost();
            boolean isFollow = m.isFollow();
            boolean isLiked = m.isLiked();
            %>
<div class="col-sm-3 mb-3 mb-sm-0">
<div class="card" style="width: 18rem;">
    <img src="<%=mediaPost.getMediaBlob().getDownloadUrl()%>" class="card-img-top" style="max-height: 150px" alt="image">
    <div class="card-body">
        <h5 class="card-title"><%=mediaPost.getName()%></h5>
        <p class="card-text"><%=mediaPost.getDescription()%></p>
    </div>
</div>
        <form>
            <button type="button" onclick="follow(<%=mediaPost.getUser().getId()%>)"><%=isFollow?"unFollow":"Follow"%></button>
        </form>

        <form>
            <button type="button" onclick="likePost(<%=mediaPost.getId()%>)"><%=isLiked?"unlike":"like"%></button>
        </form>
</div>
<%
        }
%>
</div>
    <%
    }
 %>

</body>
</html>
