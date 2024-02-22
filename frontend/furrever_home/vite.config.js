import { defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
// const env = loadEnv(mode, process.cwd(), '');

// https://vitejs.dev/config/
export default defineConfig({
  // server: {
  //   proxy: {
  //     '/api': {
  //       target: 'http://localhost:8080/api',
  //       changeOrigin: true,
  //       rewrite: (path) => path.replace(/^\/api/, ''),
  //     },
  //   }  
  // } ,
  plugins: [react()],
  preview: {
    port: 3000,
    strictPort: true,
   },
   server: {
    port: 5173,
    strictPort: true,
    host: true,
    origin: "http://0.0.0.0:5173",
   },
})
