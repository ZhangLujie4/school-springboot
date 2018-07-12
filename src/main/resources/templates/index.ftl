<html>

<body>
    <form enctype="multipart/form-data" action="/zjut/student/excel" method="post">
        文件<input type="file" name="file"/>
        <input type="submit" value="student上传"/>
    </form>

    <form enctype="multipart/form-data" action="/zjut/teacher/excel" method="post">
        文件<input type="file" name="file"/>
        <input type="submit" value="teacher上传"/>
    </form>

    <form enctype="multipart/form-data" action="/zjut/major/excel" method="post">
        文件<input type="file" name="file"/>
        <input type="submit" value="major上传"/>
    </form>

    <form enctype="multipart/form-data" action="/zjut/institute/excel" method="post">
        文件<input type="file" name="file"/>
        <input type="submit" value="institute上传"/>
    </form>

    <form enctype="multipart/form-data" action="/zjut/class/excel" method="post">
        文件<input type="file" name="file"/>
        <input type="submit" value="class上传"/>
    </form>

    <form enctype="multipart/form-data" action="/zjut/lesson/excel" method="post">
        文件<input type="file" name="file"/>
        <input type="submit" value="lesson上传"/>
    </form>
</body>


<form action="/zjut/data/download" method="post">
    <input type="number" name="dataId"/>
    <input type="submit" value="下载"/>
</form>

<a href="/zjut/data/template/lesson.xlsx">999</a>

</html>