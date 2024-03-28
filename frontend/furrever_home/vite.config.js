import { defineConfig, loadEnv} from 'vite'
import react from '@vitejs/plugin-react'
// const env = loadEnv(mode, process.cwd(), '');

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {

  const env = loadEnv(mode, process.cwd(), '')

  // console.log(App ENV: >>> ${JSON.stringify(env.VITE_BACKEND_BASE_URL)});
  // console.log(App ENV SERVER PORT: >>> ${JSON.stringify(env.SERVER_PORT)});
  // console.log(App ENV APP PORT: >>> ${JSON.stringify(env.APP_PORT)});
  
  return {
    plugins: [react()],
  preview: {
    port: env.FRONTEND_APP_PORT,
    // strictPort: true,
   },
   server: {
    port: env.SERVER_PORT,
    strictPort: true,
    host: true,
    // origin: "http://0.0.0.0:5173",
   },
  };
})