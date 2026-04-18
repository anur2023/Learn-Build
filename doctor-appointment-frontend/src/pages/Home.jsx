import { useNavigate } from "react-router-dom";

const s = {
  page: { minHeight: "100vh", display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center", background: "#f8fafc", padding: "24px" },
  card: { background: "#fff", padding: "40px 48px", borderRadius: "12px", border: "1px solid #e2e8f0", maxWidth: "440px", width: "100%", textAlign: "center", boxShadow: "0 1px 4px rgba(0,0,0,0.06)" },
  brand: { fontSize: "28px", fontWeight: "700", color: "#2563eb", marginBottom: "8px" },
  sub: { fontSize: "14px", color: "#94a3b8", marginBottom: "32px" },
  btnPrimary: { display: "block", width: "100%", padding: "11px", background: "#2563eb", color: "#fff", border: "none", borderRadius: "8px", fontSize: "15px", fontWeight: "600", cursor: "pointer", marginBottom: "12px" },
  btnSecondary: { display: "block", width: "100%", padding: "11px", background: "#fff", color: "#2563eb", border: "1px solid #bfdbfe", borderRadius: "8px", fontSize: "15px", fontWeight: "600", cursor: "pointer", marginBottom: "24px" },
  divider: { borderTop: "1px solid #f1f5f9", paddingTop: "20px", marginTop: "4px" },
  adminNote: { fontSize: "12px", color: "#94a3b8", lineHeight: "1.6" },
  adminLink: { color: "#2563eb", cursor: "pointer", fontWeight: "600", textDecoration: "underline" },
};

export default function Home() {
  const navigate = useNavigate();
  return (
    <div style={s.page}>
      <div style={s.card}>
        <div style={s.brand}>DocBook</div>
        <div style={s.sub}>Doctor Appointment Booking System</div>

        <button style={s.btnPrimary} onClick={() => navigate("/register")}>
          Create Account
        </button>
        <button style={s.btnSecondary} onClick={() => navigate("/login")}>
          Sign In
        </button>

        <div style={s.divider}>
          <p style={s.adminNote}>
            Are you an admin?{" "}
            <span style={s.adminLink} onClick={() => navigate("/login")}>
              Sign in here
            </span>
            <br />
            Admin accounts are managed by the system administrator.
          </p>
        </div>
      </div>
    </div>
  );
}