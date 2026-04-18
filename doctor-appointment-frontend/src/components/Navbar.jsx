import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const styles = {
  nav: {
    background: "#fff",
    borderBottom: "1px solid #e2e8f0",
    padding: "0 24px",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    height: "56px",
    position: "sticky",
    top: 0,
    zIndex: 100,
  },
  brand: { fontWeight: "700", fontSize: "18px", color: "#2563eb" },
  links: { display: "flex", gap: "20px", alignItems: "center" },
  link: { color: "#555", fontSize: "14px", fontWeight: "500" },
  userInfo: { fontSize: "13px", color: "#888" },
  logoutBtn: {
    background: "#ef4444",
    color: "#fff",
    border: "none",
    padding: "6px 14px",
    borderRadius: "4px",
    fontSize: "13px",
    cursor: "pointer",
  },
};

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  if (!user) return null;

  const role = user.role?.toLowerCase();

  return (
    <nav style={styles.nav}>
      <span style={styles.brand}>DocBook</span>
      <div style={styles.links}>
        {role === "patient" && (
          <>
            <Link style={styles.link} to="/patient/doctors">Doctors</Link>
            <Link style={styles.link} to="/patient/appointments">My Appointments</Link>
          </>
        )}
        {role === "doctor" && (
          <>
            <Link style={styles.link} to="/doctor/slots">My Slots</Link>
            <Link style={styles.link} to="/doctor/appointments">Appointments</Link>
          </>
        )}
        {role === "admin" && (
          <>
            <Link style={styles.link} to="/admin/specialties">Specialties</Link>
            <Link style={styles.link} to="/admin/doctors">Doctors</Link>
            <Link style={styles.link} to="/admin/appointments">All Appointments</Link>
          </>
        )}
        <span style={styles.userInfo}>{user.email} ({user.role})</span>
        <button style={styles.logoutBtn} onClick={handleLogout}>Logout</button>
      </div>
    </nav>
  );
}