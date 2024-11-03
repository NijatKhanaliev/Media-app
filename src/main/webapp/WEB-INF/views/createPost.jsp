<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Publish Post</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="<%=request.getContextPath()%>/assets/js/createPost.js" defer></script>
</head>
<body>
<h1>Publish post</h1>
<form id="createPostForm" enctype="multipart/form-data">
    <div class="mb-3">
        <label for="formGroup1" class="form-label">name</label>
        <input type="text" class="form-control" name="name" id="formGroup1" placeholder="in mountain.">
    </div>
    <div class="mb-3">
        <label for="formGroup2" class="form-label">description</label>
        <input type="text" class="form-control" name="description" id="formGroup2" placeholder="it is my image or video.">
    </div>
    <div class="mb-3">
        <label for="formGroup3" class="form-label">image or video</label>
        <input type="file" class="form-control" name="file" id="formGroup3">
    </div>
    <button type="button" class="btn btn-primary" onclick="submitPost()">create</button>
</form>
<script>
    function submitPost(){
        const name = document.getElementById("formGroup1")
        const desc = document.getElementById("formGroup2")
        const file = document.getElementById("formGroup3").files[0]

        createPost(name,desc,file)
    }
</script>
</body>
</html>
