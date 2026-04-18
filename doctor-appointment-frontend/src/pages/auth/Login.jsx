import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login as loginApi } from "../../services/authService";
import { useAuth } from "../../context/AuthContext";

const s = {
  page: { minHeight: "100vh", display: "flex", justifyContent: "center", alignItems: "center", background: "#f8fafc" },
  card: { background: "#fff", padding: "36px", borderRadius: "8px", border: "1px solid #e2e8f0", width: "360px", boxShadow: "0 1px 4px rgba(0,0,0,0.06)" },
  title: { fontSize: "22px", fontWeight: "700", marginBottom: "8px", color: "#1e293b" },
  sub: { fontSize: "13px", color: "#94a3b8", marginBottom: "24px" },
  label: { display: "block", fontSize: "13px", color: "#475569", marginBottom: "5px", fontWeight: "500" },
  input: { width: "100%", padding: "9px 12px", border: "1px solid #cbd5e1", borderRadius: "6px", fontSize: "14px", marginBottom: "16px", boxSizing: "border-box", outline: "none" },
  btn: { width: "100%", padding: "10px", background: "#2563eb", color: "#fff", border: "none", borderRadius: "6px", fontSize: "15px", fontWeight: "600", cursor: "pointer" },
  error: { background: "#fef2f2", border: "1px solid #fecaca", color: "#dc2626", padding: "8px 12px", borderRadius: "6px", fontSize: "13px", marginBottom: "14px" },
  footer: { textAlign: "center", marginTop: "18px", fontSize: "13px", color: "#64748b" },
};

export default function Login() {
  const [form, setForm] = useState({ email: "", password: "" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async () => {
    setError("");
    setLoading(true);
    try {
      const res = await loginApi(form);
      const { token, email, role, userId } = res.data;
      login({ email, role, userId }, token);
      const r = role.toLowerCase();
      if (r === "patient") navigate("/patient/doctors");
      else if (r === "doctor") navigate("/doctor/slots");
      else if (r === "admin") navigate("/admin/specialties");
    } catch (err) {
      setError(err.response?.data?.message || "Login failed. Check credentials.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={s.page}>
      <div style={s.card}>
        <div style={s.title}>Welcome back</div>
        <div style={s.sub}>Sign in to your DocBook account</div>
        {error && <div style={s.error}>{error}</div>}
        <label style={s.label}>Email</label>
        <input style={s.input} name="email" type="email" placeholder="you@example.com"
          value={form.email} onChange={handleChange} />
        <label style={s.label}>Password</label>
        <input style={s.input} name="password" type="password" placeholder="••••••••"
          value={form.password} onChange={handleChange} />
        <button style={s.btn} onClick={handleSubmit} disabled={loading}>
          {loading ? "Signing in..." : "Sign In"}
        </button>
        <div style={s.footer}>
          No account? <Link to="/register" style={{ color: "#2563eb", fontWeight: "600" }}>Register</Link>
        </div>
      </div>
    </div>
  );
}