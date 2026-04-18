import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAllDoctors, filterDoctors } from "../../services/doctorService";
import { getAllSpecialties } from "../../services/specialtyService";

const s = {
  page: { padding: "28px", maxWidth: "960px", margin: "0 auto" },
  heading: { fontSize: "20px", fontWeight: "700", color: "#1e293b", marginBottom: "20px" },
  filterBar: { display: "flex", gap: "12px", marginBottom: "20px", flexWrap: "wrap" },
  select: { padding: "8px 12px", border: "1px solid #cbd5e1", borderRadius: "6px", fontSize: "14px", background: "#fff" },
  filterBtn: { padding: "8px 18px", background: "#2563eb", color: "#fff", border: "none", borderRadius: "6px", fontSize: "14px", cursor: "pointer" },
  table: { width: "100%", borderCollapse: "collapse", background: "#fff", borderRadius: "8px", overflow: "hidden", border: "1px solid #e2e8f0" },
  th: { background: "#f8fafc", padding: "12px 16px", textAlign: "left", fontSize: "12px", fontWeight: "600", color: "#64748b", textTransform: "uppercase", letterSpacing: "0.5px", borderBottom: "1px solid #e2e8f0" },
  td: { padding: "12px 16px", fontSize: "14px", color: "#334155", borderBottom: "1px solid #f1f5f9" },
  modeBadge: (mode) => ({
    padding: "2px 10px", borderRadius: "12px", fontSize: "12px", fontWeight: "600",
    background: mode === "ONLINE" ? "#dbeafe" : "#dcfce7",
    color: mode === "ONLINE" ? "#1d4ed8" : "#16a34a",
  }),
  bookBtn: { padding: "6px 14px", background: "#16a34a", color: "#fff", border: "none", borderRadius: "5px", fontSize: "13px", cursor: "pointer", fontWeight: "500" },
  empty: { textAlign: "center", padding: "40px", color: "#94a3b8" },
};

export default function DoctorList() {
  const [doctors, setDoctors] = useState([]);
  const [specialties, setSpecialties] = useState([]);
  const [specialtyId, setSpecialtyId] = useState("");
  const [mode, setMode] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    getAllDoctors().then((res) => setDoctors(res.data));
    getAllSpecialties().then((res) => setSpecialties(res.data));
  }, []);

  const handleFilter = async () => {
    const res = await filterDoctors(specialtyId || null, mode || null);
    setDoctors(res.data);
  };

  const handleReset = async () => {
    setSpecialtyId("");
    setMode("");
    const res = await getAllDoctors();
    setDoctors(res.data);
  };

  return (
    <div style={s.page}>
      <div style={s.heading}>Browse Doctors</div>
      <div style={s.filterBar}>
        <select style={s.select} value={specialtyId} onChange={(e) => setSpecialtyId(e.target.value)}>
          <option value="">All Specialties</option>
          {specialties.map((sp) => <option key={sp.id} value={sp.id}>{sp.name}</option>)}
        </select>
        <select style={s.select} value={mode} onChange={(e) => setMode(e.target.value)}>
          <option value="">All Modes</option>
          <option value="ONLINE">Online</option>
          <option value="OFFLINE">Offline</option>
        </select>
        <button style={s.filterBtn} onClick={handleFilter}>Apply Filter</button>
        <button style={{ ...s.filterBtn, background: "#64748b" }} onClick={handleReset}>Reset</button>
      </div>
      <table style={s.table}>
        <thead>
          <tr>
            <th style={s.th}>Doctor ID</th>
            <th style={s.th}>Mode</th>
            <th style={s.th}>Experience</th>
            <th style={s.th}>Consultation Fee</th>
            <th style={s.th}>Action</th>
          </tr>
        </thead>
        <tbody>
          {doctors.length === 0 ? (
            <tr><td colSpan={5} style={s.empty}>No doctors found</td></tr>
          ) : (
            doctors.map((d) => (
              <tr key={d.id}>
                <td style={s.td}>Dr. #{d.id} (User #{d.userId})</td>
                <td style={s.td}><span style={s.modeBadge(d.mode)}>{d.mode}</span></td>
                <td style={s.td}>{d.experienceYears} yrs</td>
                <td style={s.td}>₹{d.consultationFee}</td>
                <td style={s.td}>
                  <button style={s.bookBtn} onClick={() => navigate(`/patient/book/${d.id}`)}>
                    Book Slot
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}