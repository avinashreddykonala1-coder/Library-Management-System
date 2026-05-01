<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle} – Library Manager</title>
    <style>
        :root {
            --primary: #2c3e50;
            --accent:  #2980b9;
            --accent-hover: #1a5276;
            --success: #27ae60;
            --danger:  #c0392b;
            --warning: #f39c12;
            --bg:      #ecf0f1;
            --card:    #ffffff;
            --text:    #2c3e50;
            --muted:   #7f8c8d;
            --border:  #bdc3c7;
        }
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: var(--bg); color: var(--text); }

        /* ── NAV ── */
        nav {
            background: var(--primary);
            padding: 0 2rem;
            display: flex;
            align-items: center;
            gap: 2rem;
            height: 60px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.3);
        }
        nav .brand {
            color: #fff;
            font-size: 1.3rem;
            font-weight: 700;
            text-decoration: none;
            letter-spacing: 1px;
        }
        nav a {
            color: #bdc3c7;
            text-decoration: none;
            font-size: 0.95rem;
            padding: 6px 12px;
            border-radius: 4px;
            transition: background 0.2s;
        }
        nav a:hover, nav a.active { background: var(--accent); color: #fff; }

        /* ── LAYOUT ── */
        .container { max-width: 1100px; margin: 2rem auto; padding: 0 1.5rem; }
        h1 { font-size: 1.7rem; margin-bottom: 1.2rem; color: var(--primary); }

        /* ── ALERTS ── */
        .alert { padding: 12px 18px; border-radius: 6px; margin-bottom: 1rem; font-size: 0.95rem; }
        .alert-success { background: #d5f5e3; color: #1e8449; border-left: 4px solid var(--success); }
        .alert-danger  { background: #fadbd8; color: #922b21; border-left: 4px solid var(--danger); }

        /* ── TABLE ── */
        table { width: 100%; border-collapse: collapse; background: var(--card);
                border-radius: 8px; overflow: hidden; box-shadow: 0 2px 12px rgba(0,0,0,0.08); }
        thead { background: var(--primary); color: #fff; }
        thead th { padding: 14px 16px; text-align: left; font-size: 0.9rem; letter-spacing: 0.5px; }
        tbody tr { border-bottom: 1px solid var(--border); transition: background 0.15s; }
        tbody tr:hover { background: #f4f6f7; }
        tbody td { padding: 12px 16px; font-size: 0.93rem; }

        /* ── BUTTONS ── */
        .btn { display: inline-block; padding: 8px 18px; border-radius: 5px;
               font-size: 0.9rem; cursor: pointer; text-decoration: none; border: none;
               transition: background 0.2s, transform 0.1s; }
        .btn:active { transform: scale(0.97); }
        .btn-primary  { background: var(--accent); color: #fff; }
        .btn-primary:hover  { background: var(--accent-hover); }
        .btn-warning  { background: var(--warning); color: #fff; }
        .btn-warning:hover  { background: #d68910; }
        .btn-sm { padding: 5px 12px; font-size: 0.82rem; }
        .btn-group { display: flex; gap: 0.5rem; }

        /* ── FORM ── */
        .card { background: var(--card); border-radius: 10px; padding: 2rem;
                box-shadow: 0 2px 12px rgba(0,0,0,0.08); max-width: 680px; }
        .form-group { margin-bottom: 1.1rem; }
        .form-group label { display: block; margin-bottom: 6px; font-weight: 600;
                            font-size: 0.88rem; color: var(--muted); text-transform: uppercase; }
        .form-control { width: 100%; padding: 10px 14px; border: 1px solid var(--border);
                        border-radius: 6px; font-size: 0.95rem; color: var(--text);
                        transition: border-color 0.2s; }
        .form-control:focus { outline: none; border-color: var(--accent); box-shadow: 0 0 0 3px rgba(41,128,185,0.15); }
        .form-actions { display: flex; gap: 1rem; margin-top: 1.5rem; }
        .error-msg { color: var(--danger); font-size: 0.82rem; margin-top: 4px; }

        /* ── TOP BAR ── */
        .topbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.2rem; }

        /* ── BADGE ── */
        .badge { display: inline-block; padding: 3px 9px; border-radius: 20px;
                 font-size: 0.78rem; font-weight: 600; }
        .badge-fiction    { background: #d6eaf8; color: #1a5276; }
        .badge-nonfiction { background: #d5f5e3; color: #1e8449; }
        .badge-genre      { background: #fef9e7; color: #7d6608; border: 1px solid #f9e79f; }
    </style>
</head>
<body>
<nav>
    <a class="brand" href="/">📚 Library Manager</a>
    <a href="/books" ${pageTitle.contains('Book') ? 'class="active"' : ''}>Books</a>
    <a href="/authors" ${pageTitle.contains('Author') ? 'class="active"' : ''}>Authors</a>
</nav>
<div class="container">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
