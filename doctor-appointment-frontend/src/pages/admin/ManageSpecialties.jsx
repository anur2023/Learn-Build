import { useEffect, useState } from "react";
import { getAllSpecialties, createSpecialty } from "../../services/specialtyService";

const s = {
  page: { padding: "28px", maxWidth: "720px", margin: "0 auto" },
  heading: { fontSize: "20px", fontWeight: "700", color: "#1e293b", marginBottom: "20px" },
  card: { background: "#fff", padding: "20px", borderRadius: "8px", border: "1px solid #e2e8f0", marginBottom: "24px" },
  cardTitle: { fontSize: "15px", fontWeight: "600", color: "#334155", marginBottom: "14px" },
  row: { display: "flex", gap: "10px" },
  input: { flex: 1, padding: "9px 12px", border: "1px solid #cbd5e1", borderRadius: "6px", fontSize: "14px" },
  addBtn: { padding: "9px 18px", background: "#2563eb", color: "#fff", border: "none", borderRadius: "6px", fontSize: "14px", cursor: "pointer", fontWeight: "500" },
  table: { width: "100%", borderCollapse: "collapse", background: "#fff", borderRadius: "8px", border: "1px solid #e2e8f0" },
  th: { background: "#f8fafc", padding: "11px 16px", textAlign: "left", fontSize: "12px", fontWeight: "600", color: "#64748b", textTransform: "uppercase", borderBottom: "1px solid #e2e8f0" },
  td: { padding: "11px 16px", fontSize: "14px", color: "#334155", borderBottom: "1px solid #f1f5f9" },
  msg: (ok) => ({ fontSize: "13px", marginTop: "10px", color: ok ? "#16a34a" : "#dc2626" }),
};

export default function ManageSpecialties() {
  const [specialties, setSpecialties] = useState([]);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [msg, setMsg] = useState({ text: "", ok: true });

  const fetchAll = () => getAllSpecialties().then((res) => setSpecialties(res.data));
  useEffect(() => { fetchAll(); }, []);

  const handleCreate = async () => {
    if (!name.trim()) return;
    try {
      await createSpecialty({ name, description });
      setName(""); setDescription("");
      setMsg({ text: "Specialty created!", ok: true });
      fetchAll();
    } catch (err) {
      setMsg({ text: err.response?.data?.message || "Failed.", ok: false });
    }
  };

  return (
    <div style={s.page}>
      <div style={s.heading}>Manage Specialties</div>
      <div style={s.card}>
        <div style={s.cardTitle}>Add New Specialty</div>
        <div style={s.row}>
          <input style={s.input} placeholder="Specialty name (e.g. Cardiology)" value={name} onChange={(e) => setName(e.target.value)} />
          <input style={s.input} placeholder="Description (optional)" value={description} onChange={(e) => setDescription(e.target.value)} />
          <button style={s.addBtn} onClick={handleCreate}>Add</button>
        </div>
        {msg.text && <div style={s.msg(msg.ok)}>{msg.text}</div>}
      </div>
      <table style={s.table}>
        <thead>
          <tr>
            <th style={s.th}>ID</th>
            <th style={s.th}>Name</th>
            <th style={s.th}>Description</th>
          </tr>
        </thead>
        <tbody>
          {specialties.map((sp) => (
            <tr key={sp.id}>
              <td style={s.td}>{sp.id}</td>
              <td style={s.td}>{sp.name}</td>
              <td style={s.td}>{sp.description || "—"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}